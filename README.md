
# Android Example Project: OpenWeatherMap API Integration

This project is an Android application demonstrating how to consume the REST API from OpenWeatherMap. It showcases for dependency injection, data management.

🛠 Technologies Used

Dependency Injection
Dagger 2: Manages dependency injection to provide a modular and maintainable code structure.

Data Layer
REST API Integration: Uses Retrofit 2 to interact with the OpenWeatherMap API, utilizing callbacks for asynchronous communication.
JSON Handling: Reads city lists from a local JSON file.
Local Storage:
Room Database: Stores weather data locally for quick access and offline functionality.
SharedPreferences: Saves user preferences or simple data persistently.

Presentation Layer
XML Layouts: Designs the user interface using XML.
Data Binding: Binds UI components to ViewModels, reducing boilerplate code and enhancing maintainability.
ViewModels: Implements the MVVM (Model-View-ViewModel) pattern to separate business logic from the UI.

🌐 API Reference
This project fetches weather data from OpenWeatherMap. Ensure to acquire and set an API key for proper functioning.
