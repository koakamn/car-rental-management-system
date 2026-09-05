# Car Rental Management System

Backend-приложение для управления арендой автомобилей на Spring Boot.

Система позволяет регистрировать пользователей, автомобили и водителей, создавать бронирования автомобилей с водителем или без него, рассчитывать стоимость аренды и управлять состоянием бронирований.

## Стек

- Java 25
- Spring Boot 4.1.0
- Spring Data JPA
- Hibernate 7.4.5
- PostgreSQL
- Gradle Kotlin DSL
- JUnit 5

## Основные возможности

- Регистрация и управление пользователями
- Проверка возраста пользователя (18+)
- Управление статусом пользователя (ACTIVE / INACTIVE)
- Добавление и управление автомобилями
- Отслеживание доступности автомобилей (AVAILABLE / NON_AVAILABLE)
- Регистрация и управление водителями
- Проверка доступности водителей
- Расчёт стоимости услуги водителя
- Создание бронирований с водителем (WITH_DRIVER) или без него (SELF_DRIVE)
- Расчёт общей стоимости аренды
- Расчёт авансового депозита (20% от стоимости)
- Отмена и завершение бронирований
- Освобождение автомобиля после завершения или отмены бронирования

## Структура проекта

```
src/main/java/com/example/
├── CarRentalApplication.java
├── config/
│   └── CarRentalConfig.java
├── controller/
│   ├── UserController.java
│   ├── VehicleController.java
│   ├── DriverController.java
│   └── BookingController.java
├── entity/
│   ├── Admin.java
│   ├── User.java
│   ├── UserStatus.java
│   ├── Vehicle.java
│   ├── VehicleAvailability.java
│   ├── Driver.java
│   ├── Employees.java
│   ├── Booking.java
│   └── DriverOption.java
├── repository/
│   └── ...
└── service/
    ├── UserService.java
    ├── VehicleService.java
    ├── DriverService.java
    ├── BookingService.java
    ├── AdminService.java
    └── EmployeesService.java
```

Проект использует стандартное разделение на слои:

```
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

`BookingService` отвечает за основную бизнес-логику бронирования и взаимодействует с сервисами автомобилей, водителей и пользователей.

## Модель данных

| Сущность | Основные поля |
|---|---|
| `User` | `name`, `contactNumber`, `email`, `dateOfBirth`, `occupation`, `residence`, `userStatus` |
| `Vehicle` | `registrationNumber`, `modelNumber`, `availability` |
| `Driver` | `name`, `contactNumber`, `age`, `residence`, `driverLicenseNumber`, `vehicle` |
| `Booking` | `bookingDate`, `vehicle`, `driverOption`, `advanceDeposit`, `amount`, `user`, `timing` |
| `Admin` | `name`, `contactNumber`, `email`, `gender` |
| `Employees` | `name`, `email`, `contactNumber`, `designation` |

## Настройка

Для запуска проекта необходимы:

- Java 25
- PostgreSQL
- Gradle

Создайте базу данных:

```sql
CREATE DATABASE carrental;
```

Для локального запуска можно использовать следующие тестовые параметры:

```properties
db.driver=org.postgresql.Driver
db.url=jdbc:postgresql://localhost:5432/carrental
db.username=test_user
db.password=test_password

hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.show_sql=true
hibernate.hbm2ddl.auto=update
```

Пароль и другие параметры подключения можно изменить под свою локальную конфигурацию.

## Запуск

Через Gradle Wrapper:

```bash
./gradlew bootRun
```

Для Windows:

```bash
gradlew.bat bootRun
```

После запуска приложение будет доступно по адресу:

```
http://localhost:8080
```

## API

### Users

Base URL: `/api/users`

| Метод | Endpoint | Описание |
|---|---|---|
| POST | `/api/users` | Создать пользователя |
| GET | `/api/users/{id}` | Получить пользователя |
| PUT | `/api/users/{id}` | Обновить пользователя |
| GET | `/api/users/{id}/availability` | Проверить статус пользователя |

### Vehicles

Base URL: `/api/vehicles`

| Метод | Endpoint | Описание |
|---|---|---|
| POST | `/api/vehicles` | Добавить автомобиль |
| GET | `/api/vehicles/{id}` | Получить автомобиль |
| PUT | `/api/vehicles/{id}` | Обновить автомобиль |
| DELETE | `/api/vehicles/{id}` | Удалить автомобиль |
| GET | `/api/vehicles/{id}/availability` | Проверить доступность автомобиля |

### Drivers

Base URL: `/api/drivers`

| Метод | Endpoint | Описание |
|---|---|---|
| POST | `/api/drivers` | Зарегистрировать водителя |
| GET | `/api/drivers/{id}` | Получить водителя |
| PUT | `/api/drivers/{id}` | Обновить водителя |
| DELETE | `/api/drivers/{id}` | Удалить водителя |
| GET | `/api/drivers/{id}/availability` | Проверить доступность водителя |
| GET | `/api/drivers/fee?rentalDays={days}` | Рассчитать стоимость услуги водителя |

### Bookings

Base URL: `/api/bookings`

| Метод | Endpoint | Описание |
|---|---|---|
| POST | `/api/bookings` | Создать бронирование |
| GET | `/api/bookings/{id}` | Получить бронирование |
| PATCH | `/api/bookings/{id}/cancel` | Отменить бронирование |
| PATCH | `/api/bookings/{id}/complete` | Завершить бронирование |

## Пример создания бронирования

Бронирование может быть создано с водителем:

```
driverOption=WITH_DRIVER
```

или без водителя:

```
driverOption=SELF_DRIVE
```

При создании бронирования система проверяет:

- Существует ли пользователь.
- Достиг ли пользователь 18 лет.
- Активен ли пользователь.
- Существует ли выбранный автомобиль.
- Доступен ли автомобиль.
- Требуется ли водитель.
- Доступен ли выбранный водитель.
- Стоимость аренды и услуги водителя.
- Размер авансового депозита.

## Бизнес-логика бронирования

Стоимость бронирования зависит от выбранного автомобиля, продолжительности аренды и опции водителя.

При выборе `WITH_DRIVER` к стоимости аренды добавляется стоимость услуги водителя.

Авансовый депозит рассчитывается как 20% от общей стоимости бронирования.

После завершения или отмены бронирования автомобиль освобождается. Если для бронирования был назначен водитель, он также освобождается.
