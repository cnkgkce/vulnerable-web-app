# Resmi Maven imajını temel al
FROM maven:3.8.4-openjdk-11

# Çalışma dizini ayarla
WORKDIR /app

# Proje dosyalarını Docker içine kopyala
COPY . /app

# Maven ile proje build işlemi
RUN mvn clean install

# Uygulamayı başlat
CMD ["java", "-jar", "target/vulnerable-web-app.jar"]
