# 📱 Appium Android Test Automation Framework

A robust, data-driven mobile test automation framework built with **Appium 2.x**, **Java**, **TestNG**, and **Maven** — designed for Android application testing with Page Object Model (POM) architecture.

---

## 🚀 Tech Stack

| Tool | Version | Purpose |
|------|---------|---------|
| Appium | 2.x | Mobile automation server |
| Java | 17 | Programming language |
| TestNG | 7.x | Test framework & assertions |
| Maven | 3.x | Build & dependency management |
| Extent Reports | 5.x | HTML test reporting |
| Log4j2 | 2.x | Logging |
| ADB | latest | Device/emulator communication |

---

## 📁 Project Structure

```
appium-android-automation/
├── src/
│   └── test/
│       ├── java/com/qa/
│       │   ├── base/          # BaseTest, DriverManager
│       │   ├── pages/         # Page Object classes
│       │   ├── tests/         # Test classes
│       │   └── utils/         # Helpers: Config, Report, Wait
│       └── resources/
│           ├── testng.xml
│           └── log4j2.xml
├── config/
│   └── config.properties      # Device caps & app config
├── reports/                   # Extent HTML reports (generated)
├── pom.xml
└── README.md
```

---

## ⚙️ Prerequisites

1. **Java 17+** — [Download JDK](https://adoptium.net/)
2. **Maven 3.8+** — [Download Maven](https://maven.apache.org/)
3. **Appium 2.x** — Install via npm:
   ```bash
   npm install -g appium
   appium driver install uiautomator2
   ```
4. **Android Studio / Android SDK** — Set `ANDROID_HOME` environment variable
5. **AVD or physical device** connected (verify with `adb devices`)

---

## 🏃 Running Tests

### Run all tests
```bash
mvn clean test
```

### Run specific test suite
```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml
```

### Run a specific test class
```bash
mvn clean test -Dtest=LoginTest
```

### Run with a specific device
```bash
mvn clean test -DdeviceName="Pixel_6_API_33" -DplatformVersion="13.0"
```

---

## 📊 Test Scenarios Covered

### 🔐 Login Module
- Valid login with correct credentials
- Invalid login — wrong password
- Invalid login — unregistered email
- Login with empty fields validation
- Login with special characters in username

### 🏠 Home / Dashboard Module
- Verify dashboard loads after successful login
- Validate navigation menu items
- Verify header elements and profile section

### 🔍 Search Module
- Search with valid keyword returns results
- Search with invalid keyword shows empty state
- Search suggestions / autocomplete
- Clear search field functionality

### 👤 Profile Module
- View profile page
- Edit profile details
- Profile image upload
- Logout flow

---

## 📱 Device Configuration

Update `config/config.properties` with your device details:

```properties
platformName=Android
platformVersion=13.0
deviceName=Pixel_6_API_33
appPackage=com.example.app
appActivity=com.example.app.MainActivity
appPath=src/test/resources/app/app-debug.apk
implicitWait=10
explicitWait=20
appiumServerURL=http://127.0.0.1:4723
```

---

## 📝 Reporting

After test execution, HTML reports are generated in the `reports/` folder:

```
reports/
└── ExtentReport_2025-01-01_14-30-00.html
```

Open the HTML file in any browser to view:
- Pass/Fail/Skip counts
- Step-by-step logs with timestamps
- Screenshots on failure (auto-captured)
- Device info and test duration

---

## 🧩 Framework Design — Key Highlights

### Page Object Model (POM)
Each screen is a separate class under `pages/`. Locators and actions are encapsulated, keeping tests clean and maintainable.

### Data-Driven Testing
Test data is fed via `@DataProvider` in TestNG, enabling multiple scenario coverage from a single test method.

### Explicit Waits
Custom `WaitUtils` class wraps Appium's `WebDriverWait` to handle dynamic elements, eliminating flaky tests caused by timing issues.

### Screenshot on Failure
`TestListener` (implements `ITestListener`) automatically captures and embeds screenshots in the Extent Report on any test failure.

### Parallel Execution Ready
TestNG XML is configured for parallel test execution across multiple devices or emulators.

---

## 🏆 Author

**Aakash Panchal**  
QA Engineer | Test Automation | Mobile & AR/VR Testing  
[LinkedIn](https://linkedin.com/in/iaakashpanchal) | [Email](mailto:panchalaakash78@gmail.com)

---

## 📄 License

This project is open source under the [MIT License](LICENSE).
