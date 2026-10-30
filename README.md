# Zhanserikkyzy Akbota Student API

Student: Akbota Zhanserikkyzy
Student ID: 37106
Group: IT2-2308

DevOps midterm project of Akbota Zhanserikkyzy: a REST API for managing students (Spring Boot, PostgreSQL, Docker, Jenkins).

## Change on branch feature/zhanserikkyzy-akbota-crud

Author: Akbota Zhanserikkyzy

- Added the Student entity, repository and REST controller.
- Added POST /students, GET /students and GET /students/{id}.
- Added PUT /students/{id}: updates an existing student and returns
  404 if the student does not exist.
- Added unit tests for all operations.

## Change on branch feature/zhanserikkyzy-akbota-delete

Author: Akbota Zhanserikkyzy

- Added DELETE /students/{id}: deletes a student and returns 204 No Content,
  or 404 if the student does not exist.

Automatic build trigger verified by Akbota Zhanserikkyzy via Jenkins SCM polling.
Second polling check by Akbota Zhanserikkyzy.
Second polling check by Akbota Zhanserikkyzy.
