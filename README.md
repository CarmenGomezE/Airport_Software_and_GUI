# Airport Management System & GUI

A comprehensive, object-oriented software application developed in Java for simulating and managing complex airport operations. The system features a fully functional Graphical User Interface (GUI) and implements a rigorous MVC (Model-View-Controller) and Observer architecture to handle real-time state changes across different user roles.

## Core Features & Architecture
The system manages concurrent interactions from three primary user roles, each with distinct permission scopes and functionalities:

* **Airport Manager (Gestor):** 
  * Configures global airport infrastructure (terminals, runways, boarding gates, parking zones, and hangars).
  * Registers airlines, operators, and controllers.
  * Manages global statistics and financial invoicing.
* **Flight Operator (Operador):**
  * Schedules flights (recurrent, punctual, passenger, or cargo).
  * Manages aircraft fleets (commercial and cargo planes) and handles specific requirements (e.g., temperature control, hazardous materials).
  * Updates initial flight statuses (e.g., boarding, passenger disembarkation).
* **Air Traffic Controller (Controlador):**
  * Manages real-time runway allocation and critical state transitions (e.g., waiting for takeoff/landing, taxiing to hangar).
  * Resolves flight scheduling conflicts and generates structural alternatives.

**Technical Highlights:**
* **Design Patterns:** Heavy utilization of MVC for separation of concerns and the Observer pattern for real-time notification propagation across the GUI.
* **Data Persistence:** Initial state and database simulation handled via `.dat` file parsing (e.g., `prueba_interfaz.dat`).
* **Object-Oriented Design:** Extensive use of inheritance, polymorphism, and encapsulation to model the complex hierarchy of airport physical assets and human resources.

## Running the Application
The GUI is executed via the `Main.java` class located in the root source directory. 

1. Clone the repository and compile the Java sources.
2. Ensure the state data files (e.g., `prueba_interfaz.dat`) are present in the `/resources` directory.
3. Run the application through your preferred IDE or command line.
4. Log in using designated DNI credentials (e.g., Operator, Controller, or Manager profiles) to access specific dashboards.
