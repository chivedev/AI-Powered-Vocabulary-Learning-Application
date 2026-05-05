# AI-Powered Vocabulary Learning Application (Bachelor's Thesis)

A modern, native Android application designed to facilitate personalized language learning through generative AI and efficient data management. Unlike traditional language apps with rigid learning paths, this project provides users with a flexible tool to learn vocabulary tailored to their specific needs.

## 🌟 Key Features

*   **Intelligent Word Search:** Seamlessly translates words by querying a local database (search history) first, falling back to a cloud-based API if the word is new.
*   **AI Context Generation:** Leverages the **Claude 3.5 Haiku** model to generate real-time usage examples and translations, helping users understand word context.
*   **Offline Accessibility:** All searched words and AI-generated examples are stored in a local **Room** database, enabling full functionality without an internet connection.
*   **Interactive Learning Modules:** Includes active recall tools such as **Flashcards** and a **Quiz** module with multiple difficulty levels to improve long-term retention.
*   **Robust Error Handling:** Comprehensive management of network failures, server-side API issues, and non-existent word queries via reactive UI feedback.

## 🛠 Tech Stack

*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose (Declarative UI)
*   **Architecture:** MVVM (Model-View-ViewModel)
*   **Asynchronous Processing:** Kotlin Coroutines & Flow
*   **Database:** Room (SQLite abstraction)
*   **Networking:** Retrofit & OkHttp
*   **JSON Serialization:** Gson
*   **AI Provider:** Anthropic Claude 3.5 Haiku API

## 🏗 Architecture & Design

The application implements the **MVVM** pattern to ensure modularity, scalability, and clean separation of concerns:

*   **Model:** Handles business logic and data sources (Room and Retrofit).
*   **View:** Built with **Jetpack Compose**, observing the state emitted by the ViewModels to render the UI reactively.
*   **ViewModel:** Manages the application state and acts as a bridge between the data layer and the UI, surviving configuration changes.

## 📂 Project Structure

*   **Data Layer:** Includes Room entities, DAOs, Retrofit API interfaces, and Repository implementations.
*   **Presentation Layer:** Contains Composable screens (Home, Details, Learn, Quiz, Flashcards) and their corresponding ViewModels.
*   **Navigation:** Uses a multi-view navigation system to provide a seamless user experience between modules.

---
*Developed as a Bachelor's Thesis project at the Poznan University of Technology.*

[bachelors-thesis-presentation.pdf](https://github.com/user-attachments/files/27380960/bachelors-thesis-presentation.pdf)
