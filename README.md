# AI-Powered Crop Health Management System

## Overview
The AI-Powered Crop Health Management System is designed to assist farmers and agricultural professionals in monitoring and managing crop health using artificial intelligence. By analyzing crop images and environmental data, the system predicts potential disease outbreaks and provides actionable insights, enabling early intervention and improved crop yields.

## Features
- **Disease Prediction**: Utilizes AI to analyze crop images and predict potential diseases.
- **Environmental Monitoring**: Incorporates environmental data to assess factors influencing crop health.
- **Actionable Insights**: Offers recommendations for disease management and prevention.

## Installation

1. **Clone the Repository**:
   ```sh
   git clone https://github.com/velmk28/AI-Powered-crop-health-management-system.git
   ```

2. **Navigate to the Project Directory**:
   ```sh
   cd AI-Powered-crop-health-management-system
   ```

3. **Set Up the Environment**:
   - Ensure you have Java installed on your system.
   - Compile the Java classes:
     ```sh
     javac PrakritiChatbot.java SentimentDatabase.java
     ```

4. **Run the Application**:
   ```sh
   java PrakritiChatbot
   ```

### Run locally (console app)

Prerequisites:
- Java JDK 8+ installed and `java`/`javac` on your PATH.
- (Optional) MySQL running if you want the sentiment lookup feature. Default connection in the code:
   - URL: `jdbc:mysql://localhost:3306/sentiment_db`
   - User: `root`
   - Password: `mysql`

Compile and run (from the project root):
```sh
javac PrakritiChatbot.java SentimentDatabase.java
java PrakritiChatbot
```

The program is interactive and will prompt you for:
- the state (e.g., `Punjab`)
- the crop you're interested in
- a short soil-condition description (e.g., `fertile`, `compacted`)

If you don't have MySQL available the program will still run but soil sentiment lookup will return a default score of 0.0.

### Quick static preview (optional)
A minimal `index.html` was added so you can preview a landing page with Live Server or a simple HTTP server:

Using Python's simple server:
```sh
python -m http.server 8000 --directory .
# then open http://localhost:8000
```

## Usage
- **Image Analysis**: Upload images of crops to receive disease predictions.
- **Environmental Data Input**: Enter relevant environmental data to enhance prediction accuracy.
- **Receive Recommendations**: Obtain actionable insights for disease management based on analysis.

## Technologies Used
- **Java**: Primary programming language for the application.
- **Artificial Intelligence**: Machine learning algorithms for disease prediction.

## Contributing
Contributions are welcome! To contribute:

1. **Fork the Repository**.
2. **Create a New Branch**:
   ```sh
   git checkout -b feature-name
   ```
3. **Commit Your Changes**:
   ```sh
   git commit -m "Description of changes"
   ```
4. **Push to Your Branch**:
   ```sh
   git push origin feature-name
   ```
5. **Open a Pull Request**.

## License
This project is licensed under the MIT License.

## Contact
- **GitHub**: [velmk28](https://github.com/velmk28)
- **Email**: velmk2005@gmail.com

---
*If you find this project helpful, please consider starring the repository!*
