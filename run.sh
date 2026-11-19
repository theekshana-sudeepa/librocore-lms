#!/usr/bin/env bash
# ==============================================================================
# LMSystem - Smart Library Management System Launcher
# ==============================================================================
set -e

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$DIR"

echo "=================================================================="
echo "    📚 Starting LMSystem - Smart Library Management System       "
echo "=================================================================="

# 1. Check Java
if ! command -v java &> /dev/null; then
    echo "[!] Error: Java is not found in PATH. Please install Java 17 or higher."
    exit 1
fi
JAVA_VER=$(java -version 2>&1 | head -n 1)
echo "[i] Java Runtime: $JAVA_VER"

# 2. Ensure Database is Ready
if [ -f "$DIR/start_db.sh" ]; then
    bash "$DIR/start_db.sh"
fi

# 3. Launch Application
echo "[+] Launching Library Management System GUI..."
echo "[i] Default Credentials:"
echo "    - Super Admin:  Username: superadmin  | Password: SuperAdmin@123"
echo "    - Admin:        Username: admin       | Password: Admin@123"
echo "------------------------------------------------------------------"

exec java -cp "dist/LMSystem.jar:dist/lib/*:lib/*:." lk.lms.lmsystem.gui.Splash "$@"
