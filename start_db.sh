#!/usr/bin/env bash
# Script to ensure local MySQL is running for LibroCore LMS
set -e

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
DATA_DIR="/tmp/lms_mysql_data"
SOCKET="/tmp/lms_mysql.sock"

# Load defaults from db.properties if present, allowing environment variable overrides
PROP_FILE="$DIR/db.properties"
read_prop() {
    local key="$1"
    if [ -f "$PROP_FILE" ]; then
        grep -E "^${key}=" "$PROP_FILE" | tail -n 1 | cut -d'=' -f2- | tr -d '\r'
    fi
}

PORT="${DB_PORT:-$(read_prop db.port)}"
PORT="${PORT:-3307}"
DB_NAME="${DB_NAME:-$(read_prop db.name)}"
DB_NAME="${DB_NAME:-library_dbdb}"
DB_USER="${DB_USER:-$(read_prop db.user)}"
DB_USER="${DB_USER:-root}"
DB_PASSWORD="${DB_PASSWORD:-$(read_prop db.password)}"

if [ -z "$DB_PASSWORD" ] || [ "$DB_PASSWORD" = "CHANGE_ME" ]; then
    echo "[!] Error: Database password is not configured."
    echo "    Copy db.properties.example to db.properties and set db.password, or export DB_PASSWORD."
    exit 1
fi

MYSQLD="/usr/local/mysql/bin/mysqld"
MYSQL="/usr/local/mysql/bin/mysql"

if [ ! -x "$MYSQLD" ]; then
    echo "[!] Warning: $MYSQLD not found. Checking PATH..."
    MYSQLD=$(command -v mysqld || true)
    MYSQL=$(command -v mysql || true)
fi

if [ -z "$MYSQLD" ]; then
    echo "[!] Error: mysqld binary not found. Please install MySQL or check PATH."
    exit 1
fi

# Check if already running on configured port
if nc -z 127.0.0.1 "$PORT" 2>/dev/null; then
    echo "[+] MySQL is already running on port $PORT."
else
    echo "[*] Initializing/starting MySQL on port $PORT..."
    if [ ! -d "$DATA_DIR/mysql" ]; then
        echo "[*] Initializing fresh data directory at $DATA_DIR..."
        rm -rf "$DATA_DIR"
        "$MYSQLD" --initialize-insecure --datadir="$DATA_DIR"
    fi

    echo "[*] Launching mysqld daemon on port $PORT..."
    "$MYSQLD" --datadir="$DATA_DIR" --port="$PORT" --socket="$SOCKET" --mysqlx=OFF > /dev/null 2>&1 &
    sleep 3

    echo "[*] Configuring local database credentials and schema..."
    "$MYSQL" -u root -S "$SOCKET" -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '${DB_PASSWORD}'; FLUSH PRIVILEGES; CREATE DATABASE IF NOT EXISTS \`${DB_NAME}\`;" 2>/dev/null || true

    echo "[*] Loading schema into ${DB_NAME}..."
    MYSQL_PWD="$DB_PASSWORD" "$MYSQL" -u "$DB_USER" -P "$PORT" -h 127.0.0.1 "$DB_NAME" < "$DIR/database/schema.sql" 2>/dev/null || true
    echo "[+] Database is ready!"
fi
