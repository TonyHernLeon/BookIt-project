# 🚀 BookIt

<p align="center">
  <b>Entorno de desarrollo configurado y verificado para el proyecto BookIt.</b>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Main_Branch-Protected-success?style=for-the-badge&logo=git" alt="Protected Branch">
  <img src="https://img.shields.io/badge/Git-Configured-blue?style=for-the-badge&logo=git" alt="Git">
  <img src="https://img.shields.io/badge/Status-Ready-informational?style=for-the-badge" alt="Status">
</p>

---

## 📌 Descripción
Este repositorio contiene la estructura inicial y la verificación del entorno de desarrollo para **BookIt**, asegurando que todas las herramientas base, configuraciones de control de versiones y políticas de ramas estén operativas antes de comenzar el desarrollo principal.

---

## 🛠️ Requisitos Previos (Herramientas del Entorno)

Para garantizar la reproducibilidad del entorno, a continuación se detallan las **7 herramientas esenciales** instaladas y verificadas en el sistema, junto con sus versiones exactas:

| Herramienta | Versión Verificada | Comando de Comprobación |
| :--- | :--- | :--- |
| **Git** | `git version 2.x.x` | `git --version` |
| **Java (JDK)** | `openjdk version "xx.x.x"` | `java -version` |
| **Docker** | `Docker version 2x.x.x` | `docker --version` |
| **Maven** | `Apache Maven 3.x.x` | `mvn -version` |

*(Nota: Puedes sustituir cualquiera de estas herramientas por las 7 específicas que utilices en tu stack).*

---

## ⚙️ Configuración Inicial y Git

El entorno local se encuentra enlazado con GitHub mediante autenticación segura por **clave SSH**.

### Configuración de identidad:
```bash
git config --global user.name "Tu Nombre"
git config --global user.email "tu.email@example.com"