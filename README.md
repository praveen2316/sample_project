# DMS - Document Management System

A modern Document Management System built with Angular, Spring Boot, and MongoDB.

## Technology Stack

### Frontend
- Angular 17
- TypeScript
- SCSS
- Chart.js (ng2-charts)
- Modern responsive design

### Backend
- Spring Boot 3.2
- Java 17
- Spring Security
- JWT Authentication
- MongoDB
- Lombok

## Features

### Dashboard Page
- **Storage Information**: Visual storage usage bar with percentage
- **Total Files**: Count of all uploaded documents
- **Total Applications/Users**: User count for admin, files for users
- **Collections**: Number of file types/collections
- **File Extension Distribution**: Interactive pie chart showing file type percentages
- **Recent Uploads**: List of recently uploaded documents with:
  - File name and icon
  - File size
  - Uploaded by user
  - Upload timestamp
  - More actions menu

### User Role Based Access
- **Admin**: Can view all documents and system-wide statistics
- **User**: Can only view their own documents and personal statistics

## Project Structure

```
/workspace
├── dms-backend/                 # Spring Boot Backend
│   ├── src/main/java/com/dms/
│   │   ├── config/             # Configuration classes
│   │   ├── controller/         # REST controllers
│   │   ├── dto/                # Data transfer objects
│   │   ├── model/              # Entity models
│   │   ├── repository/         # MongoDB repositories
│   │   ├── security/           # Security configuration
│   │   ├── service/            # Business logic
│   │   └── util/               # Utility classes
│   └── src/main/resources/
│       └── application.properties
│
└── dms-frontend/               # Angular Frontend
    └── src/app/
        ├── components/         # UI components
        │   └── dashboard/      # Dashboard component
        ├── models/             # TypeScript interfaces
        ├── services/           # API services
        ├── guards/             # Route guards
        └── interceptors/       # HTTP interceptors
```

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Node.js 18+ and npm
- MongoDB installed and running
- Maven 3.6+

### Backend Setup

1. Navigate to backend directory:
```bash
cd /workspace/dms-backend
```

2. Update `application.properties` with your MongoDB connection:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/dms_db
```

3. Build and run the application:
```bash
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to frontend directory:
```bash
cd /workspace/dms-frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm start
```

The frontend will start on `http://localhost:4200`

## API Endpoints

### Dashboard
- `GET /api/dashboard` - Get dashboard statistics (role-based)

### Authentication (To be implemented)
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `POST /api/auth/logout` - User logout

### Documents (To be implemented)
- `GET /api/documents` - Get all documents (filtered by role)
- `POST /api/documents/upload` - Upload a document
- `GET /api/documents/{id}` - Get document by ID
- `PUT /api/documents/{id}` - Update document
- `DELETE /api/documents/{id}` - Delete document

## Security Features

- JWT-based authentication
- Role-based access control (USER/ADMIN)
- CORS configuration for frontend communication
- Secure password hashing
- User-specific data isolation

## Future Enhancements

- Complete authentication module
- Document upload/download functionality
- Advanced search and filtering
- Document versioning
- Sharing and collaboration features
- Email notifications
- Audit logs
- Mobile responsive improvements

## License

This project is created for educational and demonstration purposes.
