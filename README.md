# Clean Architecture, Kotlin, Koin sample
### Technologies & Methodologies which used:
1. Kotlin (Language)
2. Android X Artifacts
3. Clean Architecture with Data binding
4. Koin (Dependency Injection)
5. Retrofit and OkHttp (Network)
6. LiveData
7. Android Studio (Development Environment)
8. Added few Unit Test Cases.

### Architecture:
The POC built on Clean architecture with the help of data binding. Also, follows repository pattern. The repository design pattern provide abstraction layer between view model and network calls. Stricly follows Single Responsibility model and also segregated code and extracted business logic from view to provide more test coverage for business logic.

### The App Scenario
Used the following REST API, develop a simple application that fetches the
albums and photos from the API and displays them in a grid.
The API is here: https://jsonplaceholder.typicode.com

### Android Stack Used:
AppCompatActivity
ConstraintLayout

### Used libraries
1. [GitHub](http://square.github.io/retrofit/) - retrofit
2. [GitHub](https://github.com/square/moshi) - moshi

###Known issues (Time constraint):
1. Few of the error cases are not handled.
2. Fewintegration tests are implemented.

## Author
@Deenanath
