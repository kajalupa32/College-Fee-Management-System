 College Fee Management System

A robust, production-ready desktop application built in Java to streamline college fee tracking, invoice generation, and student financial records management.

🚀 Features
- **Student Data Management:** Track active student profiles, names, and enrollment records.
- **Dynamic Fee Structure:** Set custom fee structures for different departments and courses.
- **Real-Time Receipt Generation:** Automated calculation of amounts paid, tracking numbers, and transactional entry logs.
- **Outstanding Dues Analytics:** Instant reporting of pending balances across student cohorts.
- **Live Database Grid:** Fully integrated with MySQL using JDBC for real-time data persistence.

🛠️ Tech Stack & Workflow
- **Frontend Framework:** Java Swing (GUI), AWT Components
- **Backend Architecture:** Java Development Kit (JDK), JDBC API
- **Database Engine:** MySQL Server
- **IDE Environment:** Eclipse
- **Development Workflow:** Developed utilizing an agile hybrid workflow—architecting the UI structures and project build states manually while leveraging AI collaboration for rapid prototyping, component generation, and workspace debugging.

📂 Project Structure
- `src/` - Core Java source code compilation classes (`MainFrame.java`, `DB.java`, `ReceiptPanel.java`, etc.).
- `college_fee.sql` - Complete database schema script for rapid system replication and table deployment.

⚙️ How to Setup and Run
1. **Database Configuration:** Import the `college_fee.sql` file into your local MySQL server. Update the credentials inside `DB.java` if necessary.
2. **IDE Import:** Import the project workspace folder into Eclipse.
3. **Run Application:** Open `MainFrame.java`, right-click anywhere inside the editor, and select **Run As** > **Java Application**.

👤 Author
- Developed by Kajal
