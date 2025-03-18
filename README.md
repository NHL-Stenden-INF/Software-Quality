# JavaFX Docker Application

This project allows you to run a **JavaFX application** inside a **Docker container** with GUI support. It automatically forwards the display from the container to your host machine.

---

## 🚀 Prerequisites

Before running the application, ensure you have the following installed:

### **1. Install Docker**
- Download and install **Docker Desktop**:  
  [Docker Installation Guide](https://docs.docker.com/get-docker/)

### **2. Install an X Server (Windows/Mac Only)**
- If you're on **Windows**, install **VcXsrv** (recommended) or **Xming**:  
  [Download VcXsrv](https://sourceforge.net/projects/vcxsrv/)
- If you're on **Mac**, install **XQuartz**:  
  [Download XQuartz](https://www.xquartz.org/)
- If you're on **Linux**, you already have X11, no extra setup needed!

### **3. Run X Server (Windows/Mac Only)**
- Open **VcXsrv/Xming/XQuartz** before launching Docker.
- For VcXsrv, start it with:
  - **Multiple Windows**
  - **Display number: `0`**
  - **Disable Access Control (-ac)**

---

## 🛠️ Setup and Installation

### **1. Clone the Repository**
```bash
git clone https://github.com/your-repo/javafx-docker.git
cd javafx-docker
```

### **2. Build and Run the Docker Container**
Run the following command to build the Docker image and start the container:
```bash
docker-compose up --build
```

The application will automatically detect your **DISPLAY** and forward the GUI output to your host machine.

---

## 📌 Project Structure
```
javafx-docker/
├── src/main/java/com/example/MainApp.java   # JavaFX main application
├── Dockerfile                               # Container setup
├── docker-compose.yml                       # Docker compose configuration
├── pom.xml                                  # Maven dependencies
└── README.md                                # You're reading this!
```

---

## ✅ Troubleshooting

### **1. "Unable to open DISPLAY" Error**
If you see this error, follow these steps:
- **Windows**:
  1. Ensure **VcXsrv/Xming** is running.
  2. Run this command in **PowerShell**:
     ```powershell
     $env:DISPLAY="host.docker.internal:0.0"
     ```
- **Mac (XQuartz)**:
  1. Ensure XQuartz is running.
  2. Run this command in **Terminal**:
     ```bash
     export DISPLAY=host.docker.internal:0.0
     ```

### **2. "JavaFX application launches but no window appears"**
- Run a test GUI inside Docker:
  ```bash
  docker exec -it javafx_container xclock
  ```
- If `xclock` doesn’t appear, **your X server is not forwarding correctly!**

### **3. Check Docker Logs for Errors**
```bash
docker logs javafx_container
```

---

## 📦 Stopping & Cleaning Up
To stop the running container, press **`CTRL+C`** in the terminal. If you need to remove everything:
```bash
docker-compose down
```

---

## 📜 License
This project is licensed under **MIT License**. Feel free to modify and distribute!

---

## 👨‍💻 Author
Developed by **Foxo**, the fiercest pirate in the JavaFX seas! 🏴‍☠️

