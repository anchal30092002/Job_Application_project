Dossier — Job Portal (Spring Boot + React)

A personal, learning-focused job portal in the spirit of LinkedIn / Naukri.com. Employees and employers both register, complete a profile, and manage their account; employers create companies and post jobs; employees browse companies/jobs and leave remarks (reviews) on companies.

Built to practice full-stack development: a Spring Boot REST API backed by PostgreSQL, and a React (Vite) frontend consuming it.

Tech stack

Backend

Java 17, Spring Boot 3.5.6
Spring Web, Spring Data JPA, Spring Security (config only — no auth filter chain wired up yet)
PostgreSQL
Maven

Frontend

React 18 + Vite
react-router-dom, axios
Plain CSS with a small design-token system (no UI framework)
Project structure
.
├── jobAppbackend/
│   └── firstjobApp/          # Spring Boot API
│       └── src/main/java/com/Anchal/firstjobApp/
│           ├── Person/       # Employee & Employer registration/profile/login
│           ├── Company/      # Company CRUD
│           ├── Job/          # Job CRUD
│           ├── Review/       # Remarks on a company
│           └── Configuration/# CORS & security config
└── frontend/                 # React app (this repo's UI)
└── src/
├── api/               # axios calls, one file per resource
├── context/           # auth/session context
├── components/        # Navbar, route guard, shared UI
├── pages/employee/    # employee-facing pages
├── pages/employer/    # employer-facing pages
└── styles/            # design tokens + component CSS

Note: if you're combining this with an existing export, you may have an older, mostly-empty frontend folder already inside springboot/. Remove or rename it before pushing so it doesn't collide with the frontend/ folder described here.

Features

Both roles

Register with email + password
Log in
Complete profile (one-time — see Known limitations)
Fetch own profile
Activate account
Delete (deactivate) account

Employee

Profile includes address, education history, and work experience
Browse all companies
View a company's open jobs
Leave, edit, and delete remarks (reviews) on a company

Employer

Create, update, and delete companies
Create, update, and delete jobs (linked to a company)
Getting started
Prerequisites
Java 17+
Maven (or use the included mvnw wrapper)
PostgreSQL running locally
Node.js 18+
1. Database

Create a database matching application.properties:

sql
CREATE DATABASE "jobApp";

By default the app connects as user anchal / password anchal on localhost:5432. Update jobAppbackend/firstjobApp/src/main/resources/application.properties to match your own PostgreSQL credentials before running — and consider moving those out of the properties file (e.g. environment variables) before making the repo public.

spring.jpa.hibernate.ddl-auto=create-drop means the schema is recreated on every startup and dropped on shutdown — don't rely on this database for anything you want to keep.

2. Run the backend
   bash
   cd jobAppbackend/firstjobApp
   ./mvnw spring-boot:run

The API starts on http://localhost:8080.

3. Run the frontend
   bash
   cd frontend
   npm install
   npm run dev

The app starts on http://localhost:5173 (Vite's default port). The backend's CORS config only allows that origin, so if you change the frontend port, update Configuration/CorsConfig.java to match.

API overview
Resource	Endpoints
Auth	POST /RegisterUser, GET /loginEmployee/{email}/{password}, GET /loginEmployer/{email}/{password}
Employee profile	POST /completeProfileEmployee, GET /myProfileEmployee/{email}/{password}, PATCH /activateEmployee/{email}/{password}, DELETE /deleteEmployee/{email}/{password}
Employer profile	POST /completeProfileEmployer, GET /myProfileEmployer/{email}/{password}, PATCH /activateEmployer/{email}/{password}, DELETE /deleteEmployer/{email}/{password}
Company	GET/POST /Company, GET/PUT/DELETE /Company/{id}
Job	GET/POST /jobs, GET/PUT/DELETE /job/{id}
Review	GET/POST /company/{companyId}/Review, GET/PUT/DELETE /company/{companyId}/Review/{reviewId}
Known limitations

These are backend behaviors the frontend works around or surfaces honestly, rather than masking:

No profile update endpoint. completeProfileEmployee/Employer only succeeds once; resubmitting returns "already completed" without updating anything.
activateEmployee/Employer currently throws a NullPointerException — activatePersonOut is declared null and never instantiated before its setters are called in personServiceImpl.
Login doesn't verify the password — loginEmployee/loginEmployer only check that the email is registered.
Companies have no owner. There's no employer reference on Company, so any employer can edit or delete any company or job.
Employer.setName has a bug (name = name; instead of this.name = name;), so an employer's name is never actually persisted.
Roadmap ideas
Fix the activation NPE and add real password verification on login
Add a proper update-profile endpoint (PUT) instead of one-time completion
Tie companies/jobs to the employer that created them
Token-based auth (JWT) instead of passing credentials on every request
Pagination and search/filtering on jobs and companies
Author

Anchal — built as a personal learning project.