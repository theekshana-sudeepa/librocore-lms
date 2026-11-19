<div align="center">

# 📚 LibroCore — Enterprise Desktop Library Management System

[![Java Version](https://img.shields.io/badge/Java-21%20LTS-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Database](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![IDE](https://img.shields.io/badge/Apache%20NetBeans-17%2B-1B6AC6?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)](https://netbeans.apache.org/)
[![UI](https://img.shields.io/badge/Swing%20%7C%20FlatLaf-3.4-007ACC?style=for-the-badge&logo=java&logoColor=white)](https://www.formdev.com/flatlaf/)
[![Reporting](https://img.shields.io/badge/JasperReports-6.21-00A86B?style=for-the-badge&logo=jasper-reports&logoColor=white)](https://community.jaspersoft.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-10B981?style=for-the-badge)](LICENSE)

<br/>

**An enterprise-architected Java Swing desktop Library Information & Circulation System.**<br/>
Engineered with a **3NF MySQL relational schema**, **Barcode4J / ZXing scanning**, **JasperReports PDF compilation**, and a modern **FlatLaf desktop design system**.

<br/>

![LibroCore Architecture Proof](assets/portfolio_screenshots/08_NetBeans_Code_Architecture_Proof.png)

</div>

---

## 🌟 Executive Summary

**LibroCore** replaces manual paper register workflows with an automated, role-governed desktop library management platform. Engineered from first principles in Java Swing and MySQL 8.0, it orchestrates the full institutional library lifecycle: patron registration, cascading geographic jurisdictions, two-tier catalog classification, dynamic barcode/QR label printing, circulation tracking, overdue penalty calculation, and automated database backup/restore.

Also includes a 6-slide visual case study carousel ([LibroCore_LMS_LinkedIn_Carousel.pdf](assets/LibroCore_LMS_LinkedIn_Carousel.pdf)).

---

## 🏛️ System Architecture

### Layered Desktop Runtime Architecture

```mermaid
flowchart TB
    subgraph Presentation["Presentation Layer - Java Swing and FlatLaf 3.4"]
        Splash["Splash Screen<br/>Bootloader"]
        Signin["Signin Portal<br/>Regex Validation and RBAC Router"]
        SuperDash["SuperAdmin Dashboard<br/>Audit, Reports and DB Backup"]
        AdminDash["Admin Dashboard<br/>Catalog, Patrons and Circulation"]
    end

    subgraph Business["Business Logic and Modular Panels"]
        UserMod["User Management<br/>AdminUserPanel and SuperAdminUserPanel"]
        BookMod["Catalog Management<br/>Main and Sub Category Hierarchy"]
        CircMod["Circulation Engine<br/>AssignBookPanel and Return Tracking"]
        ReportMod["Reporting and Backup<br/>Reports and DBBackRestore"]
    end

    subgraph Integration["Document and Hardware Integration"]
        Barcode["Barcode4J and ZXing<br/>Patron ID and Book Spine Labels"]
        Jasper["JasperReports 6.21<br/>Compiled PDF Receipts and Audits"]
        BackupCLI["MySQL CLI ProcessBuilder<br/>MYSQL_PWD Secure Dump and Restore"]
    end

    subgraph Persistence["Data Access and Relational Storage"]
        DAO["MySQL Connection Manager<br/>db.properties, Env Vars and Port Failover"]
        DB[("MySQL 8.0 Database<br/>Normalized 3NF Relational Schema")]
    end

    Splash -->|Launch Auth| Signin
    Signin -->|Route SuperAdmin Role| SuperDash
    Signin -->|Route Admin Role| AdminDash
    SuperDash -->|Open Modules| UserMod
    SuperDash -->|Open Modules| ReportMod
    AdminDash -->|Open Modules| BookMod
    AdminDash -->|Open Modules| CircMod
    UserMod -->|Generate Patron Tags| Barcode
    BookMod -->|Generate Book Labels| Barcode
    CircMod -->|Compile Loan Receipt| Jasper
    ReportMod -->|Export Audit PDF| Jasper
    ReportMod -->|Execute Dump or Restore| BackupCLI
    UserMod -->|Execute Queries| DAO
    BookMod -->|Execute Queries| DAO
    CircMod -->|Execute Queries| DAO
    ReportMod -->|Execute Queries| DAO
    DAO -->|JDBC Connection| DB
    BackupCLI -->|SQL Stream| DB
```

### Authentication & Circulation Flow

```mermaid
sequenceDiagram
    actor Staff as Librarian or SuperAdmin
    participant UI as Signin and Dashboard UI
    participant Val as Validation Engine
    participant DAO as MySQL Connection Manager
    participant DB as MySQL 8.0 Database
    participant Doc as Barcode and Jasper Engine

    Staff->>UI: Submit username and password
    UI->>Val: Validate credential syntax
    Val-->>UI: Syntax accepted
    UI->>DAO: Query login_details joined with user and status
    DAO->>DB: Execute SELECT lookup
    DB-->>DAO: Return user role and account status
    DAO-->>UI: Authenticated session payload
    UI-->>Staff: Route to SuperAdmin or Admin Dashboard

    Staff->>UI: Scan Patron NIC and Book ID for checkout
    UI->>DAO: Verify patron status and book availability
    DAO->>DB: Insert circulation row in b_issued
    DB-->>DAO: Confirm transaction
    UI->>Doc: Compile loan receipt and barcode PDF
    Doc-->>Staff: Render printable PDF output
```

### Core 3NF Relational Schema

```mermaid
erDiagram
    USER_TYPE ||--o{ USER : classifies
    STATUS ||--o{ USER : governs
    GENDER ||--o{ USER : describes
    USER ||--o| LOGIN_DETAILS : authenticates
    USER ||--o{ CONTACT_NUMBER : owns
    USER ||--o{ ADDRESS : resides_at
    PROVINCE ||--o{ DISTRICT : contains
    DISTRICT ||--o{ CITY : contains
    CITY ||--o{ ADDRESS : locates
    MAIN_CATEGORY ||--o{ SUB_CATEGORY : groups
    MAIN_CATEGORY ||--o{ BOOK : categorizes
    SUB_CATEGORY ||--o{ BOOK : refines
    USER ||--o{ B_ISSUED : borrows
    BOOK ||--o{ B_ISSUED : issued_in
    USER ||--o{ FEEDBACK : submits

    USER {
        int id PK
        varchar nic_no "Unique patron identifier"
        varchar fName
        varchar lName
        varchar email "Unique patron email"
        varchar dob
        int gender_id FK
        int user_type_id FK
        int status_id FK
    }

    LOGIN_DETAILS {
        int id PK
        varchar username "Unique login handle"
        varchar password
        int user_id FK
    }

    MAIN_CATEGORY {
        int id PK
        varchar name
        varchar Color_code
    }

    SUB_CATEGORY {
        int id PK
        varchar name
        int main_category_id FK
    }

    BOOK {
        varchar id PK
        int main_category_id FK
        int sub_category_id FK
        varchar name
        varchar book_author
        decimal price
    }

    B_ISSUED {
        int id PK
        varchar user_nic_no FK
        varchar book_id FK
        varchar issued_date
        varchar return_date
    }

    FEEDBACK {
        int id PK
        varchar fname
        varchar lname
        varchar nic_no
        text message
    }
```

---

## 📸 Interactive UI Showcase

Every interface is built natively in Java Swing with custom anti-aliased Java2D components, FlatLaf styling, and Event Dispatch Thread (EDT) safe timers.

| Modern Authentication Portal | SuperAdmin Operations Dashboard |
| :---: | :---: |
| <img src="assets/portfolio_screenshots/01_Sign_In_Portal.png" width="480"/> | <img src="assets/portfolio_screenshots/02_SuperAdmin_Dashboard.png" width="480"/> |
| *Enter-key navigation, password reveal toggle & gradient card* | *Real-time library counters, live clock & privileges rail* |

| Book Inventory & Classification | Patron Registry & Access Control |
| :---: | :---: |
| <img src="assets/portfolio_screenshots/03_Book_Catalog_Management.png" width="480"/> | <img src="assets/portfolio_screenshots/04_Member_Management.png" width="480"/> |
| *Keystroke search filters & normalized category trees* | *Synthetic demo registry with instant status filtering* |

| Circulation & Loan Issue Desk | Return Desk & Normalized 3NF Schema |
| :---: | :---: |
| <img src="assets/portfolio_screenshots/05_Circulation_Issue_Desk.png" width="480"/> | <img src="assets/portfolio_screenshots/07_Relational_Database_3NF_Model.png" width="480"/> |
| *Dual-entity verification (Patron ID + Book ID) & date pickers* | *Multi-layer normalized 3NF relational entity design* |

---

## 🚀 Key Engineering Capabilities

- **Dual-Tier Role-Based Access Control (RBAC):** Distinct operational workspaces for `SUPERADMIN` and `ADMIN` roles with account state enforcement (`ACTIVE`, `INACTIVE`, `PENDING`) and centralized regex input validation (`Validation.java`).
- **Normalized Catalog & Geographic Cascades:** Hierarchical `main_category -> sub_category -> book` catalog indexing and `province -> district -> city -> address` patron location modeling in strict 3NF.
- **Hardware Barcode & QR Label Export:** Dynamic 1D barcode and 2D QR code PDF compilation using **Barcode4J** and **ZXing** for patron cards and book inventory tags.
- **Enterprise PDF Reporting:** Integrated **JasperReports 6.21** templates (`src/reports/*.jasper`) powered by OpenPDF/iText for printable receipts, attendance logs, and audit statements.
- **Externalized Secret & Connection Management:** Zero hardcoded credentials in source code — `MySQL.java` and `DBBackRestore.java` load configuration from `db.properties`, JVM properties, or environment variables (`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`), with smart port failover (`3307 <-> 3306`) and secure `MYSQL_PWD` subprocess backup/restore.
- **EDT-Safe FlatLaf UI:** High-DPI FlatLaf 3.4 theme with custom rounded Java2D controls (`RoundButton`, `RoundTextfield`, `RoundPasswordField`) and non-blocking 1-second timer updates.

---

## 🛠️ Tech Stack

| Component | Technology | Version | Role |
| :--- | :--- | :--- | :--- |
| **Language** | Java (JDK) | **21 LTS / 17+** | Core desktop application & business logic |
| **GUI Framework** | Java Swing + FlatLaf | **3.4.1** | Modern desktop presentation layer |
| **Database** | MySQL Server | **8.0+** | Normalized 3NF relational persistence |
| **JDBC Driver** | MySQL Connector/J | **8.4.0** | Type-4 database connectivity |
| **Reporting** | JasperReports + OpenPDF | **6.21.3 / 1.3.30** | Printable PDF reports & barcode sheets |
| **Barcodes / QR** | Barcode4J + ZXing | **3.4.1** | 1D/2D barcode generation & rendering |
| **Vector Graphics** | Apache Batik | **1.17** | SVG rendering for reports and labels |

---

## 📁 Repository Structure

```text
LMSystemNew/
├── assets/
│   ├── LibroCore_LMS_LinkedIn_Carousel.pdf   # 6-slide visual case study deck
│   ├── linkedin_cover_hero.png               # Portfolio cover artwork
│   └── portfolio_screenshots/                # Framed UI & schema screenshots
├── database/
│   └── schema.sql                            # 3NF MySQL schema & synthetic demo seed data
├── lib/                                      # Runtime JAR dependencies (FlatLaf, JasperReports, JDBC)
├── nbproject/                                # Apache NetBeans Ant project metadata
├── scripts/
│   ├── generate_all_slides_clean.py          # Carousel slide deck generator
│   └── generate_portfolio_suite.py           # Framed screenshot & IDE composite generator
├── src/
│   ├── bookqrcodes/                          # Runtime output folder for generated book QR PDFs
│   ├── userqrcodes/                          # Runtime output folder for generated patron QR PDFs
│   ├── reports/                              # Compiled JasperReports (.jasper) templates
│   └── lk/lms/lmsystem/
│       ├── component/                        # Custom Java2D rounded UI components
│       ├── connection/MySQL.java             # Externalized JDBC manager with port failover
│       ├── dialog/                           # Modal CRUD forms (Users, Books, Categories, Feedback)
│       ├── gui/                              # Splash, Signin, AdminDashboard, SuperAdminDashboard
│       ├── model/                            # Domain enums (Status, UserType)
│       ├── panel/                            # Modular views (Dashboard, Catalog, Circulation, Backup)
│       └── validation/                       # Centralized regex validation rules
├── test/                                     # Database smoke test & UI capture harnesses
├── db.properties.example                     # Safe database configuration template
├── run.sh                                    # Local launcher script
├── start_db.sh                               # Isolated local MySQL bootstrapper
├── LICENSE                                   # MIT License
└── README.md
```

---

## ⚡ Local Setup & Execution

### Prerequisites
1. **Java JDK 17 or 21 LTS** (`java -version`, `javac -version`)
2. **MySQL Server 8.0+** (installed locally or available on `PATH`)

### 1. Clone & Configure Environment
```bash
git clone https://github.com/theekshana-sudeepa/LMSystemNew.git
cd LMSystemNew
cp db.properties.example db.properties
```

Edit `db.properties` with your local MySQL credentials (never commit `db.properties`):
```properties
db.host=127.0.0.1
db.port=3307
db.name=library_dbdb
db.user=root
db.password=your_local_password
```

Alternatively, configure via environment variables (`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`, and optional `MYSQL_BIN_DIR` for in-app backup/restore).

### 2. Import Schema & Build
```bash
# Option A: Import into an existing MySQL instance
mysql -u root -p < database/schema.sql

# Compile sources
mkdir -p build/classes
javac -encoding UTF-8 -d build/classes -cp "lib/*" $(find src -name "*.java")
cp -r src/lk/lms/lmsystem/icon src/lk/lms/lmsystem/img build/classes/lk/lms/lmsystem/
cp -r src/reports build/classes/

# Launch desktop application
java -cp "build/classes:lib/*:." lk.lms.lmsystem.gui.Splash
```

Or use the macOS/Linux one-click local bootstrapper (after setting `db.properties`):
```bash
chmod +x run.sh start_db.sh
./run.sh
```

### Synthetic Demo Login Credentials (from `database/schema.sql`)

| Role | Username | Demo Password | Access Scope |
| :--- | :--- | :--- | :--- |
| **Super Administrator** | `superadmin` | `SuperAdmin@123` | Full audit dashboard, staff/patron CRUD, reports & DB backup |
| **Librarian / Staff** | `admin` | `Admin@123` | Catalog management, patron registry & book circulation |

> **Security Note:** The accounts above are synthetic local demo records seeded by `database/schema.sql`. Rotate credentials and enforce hashed password storage (e.g. BCrypt/Argon2) before any non-local deployment.

---

## 📄 License

Distributed under the **MIT License**. See [LICENSE](LICENSE) for full terms.

---

<div align="center">

Engineered by **Theekshana Sudeepa**<br/>
[LinkedIn](https://www.linkedin.com/in/theekshana-sudeepa/) · [GitHub](https://github.com/theekshana-sudeepa) · [LexoraTech](https://lexoratech.com)

</div>
