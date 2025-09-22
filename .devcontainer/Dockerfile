# Imagen oficial de Gradle con JDK 17 (incluye herramientas listas)
FROM gradle:8.9-jdk17

# Directorio de trabajo
WORKDIR /workspace

# Usuario no root para VSCode
RUN useradd -ms /bin/bash vscode \
    && chown -R vscode:vscode /workspace
USER vscode

# Extensiones de VSCode las maneja devcontainer.json
