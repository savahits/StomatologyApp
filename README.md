# StomatologyApp


StomatologyApp is a web application for managing a dental clinic. It provides functionalities for handling appointments, managing doctors and specializations, and tracking client information. The application features a role-based access control system for administrators and doctors.

## Features

*   **Role-Based Access Control:** Differentiates between `ADMIN` and `DOCTOR` roles, each with specific permissions.
*   **Initial Administrator Setup:** On first launch, the application guides the user to create an initial administrator account. Subsequent access to this setup page is restricted.
*   **Doctor Management (Admin):**
    *   Create new doctor profiles with associated user accounts.
    *   View a paginated list of all doctors.
    *   View detailed information for a specific doctor.
    *   Delete doctors from the system.
*   **Specialization Management (Admin):**
    *   Add new medical specializations for doctors.
*   **Appointment Management:**
    *   Admins can create appointments, linking clients to doctors at a specific time.
    *   Clients are created or retrieved based on their phone number. The system validates that existing client data matches.
    *   Admins can view all scheduled and completed appointments.
    *   Doctors can view their own scheduled and completed appointments.
    *   Admins can mark an appointment as "Done".
    *   Appointments that are past their scheduled date are highlighted in the UI.
*   **Secure Authentication:** Powered by Spring Security for robust user login and session management.

## Tech Stack

*   **Backend:** Java 21, Spring Boot 4.0.3 (Data JPA, Web MVC, Security), Maven
*   **Frontend:** Thymeleaf, HTML, CSS, JavaScript
*   **Database:** PostgreSQL
*   **Containerization:** Docker, Docker-Compose
*   **Libraries:**
    *   `Lombok` for reducing boilerplate code.
    *   `libphonenumber` for phone number parsing and validation.

## Getting Started

This project is configured to run using Docker and Docker Compose, which is the recommended method for setup.

### Prerequisites

*   Docker
*   Docker Compose
*   Java 21
*   Apache Maven

### Running with Docker

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/savahits/StomatologyApp.git
    cd StomatologyApp
    ```

2.  **Build and run the application using Docker Compose:**
    ```sh
    docker-compose up -d --build
    ```
    This command will:
    *   Build the Docker image for the Spring Boot application.
    *   Start a PostgreSQL container for the database.
    *   Start the application container, connecting it to the database.

3.  **Access the application:**
    The application will be available at `http://localhost:8080`.

### Initial Setup

Upon the first run, the system will detect that no administrator exists and will automatically redirect you to the admin setup page.

1.  Navigate to `http://localhost:8080`. You will be redirected to `/setup/admin`.
2.  Fill in the form to create the first administrator account.
3.  After successful creation, you will be redirected to the `/login` page.
4.  Log in with the credentials you just created to start using the application.

## Application Usage

Once logged in, you can navigate the application using the header links:

*   **/doctors**: View the list of clinic doctors. Admins have options to add new doctors and specializations.
*   **/appointments**: View a list of patient appointments. The view is tailored to the user's role (Admins see all appointments, Doctors see their own). Admins can create new appointments and mark existing ones as completed.
*   **/logout**: Log out of the application.