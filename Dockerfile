FROM maven:3.8.4-openjdk-8

# Gerekli paketleri yükle
RUN apt-get update && apt-get install -y \
    wget \
    gnupg2 \
    curl \
    unzip \
    apt-transport-https \
    ca-certificates \
    fonts-liberation \
    libappindicator3-1 \
    libasound2 \
    libatk-bridge2.0-0 \
    libatk1.0-0 \
    libcups2 \
    libdbus-1-3 \
    libgdk-pixbuf2.0-0 \
    libnspr4 \
    libnss3 \
    libx11-xcb1 \
    libxcomposite1 \
    libxdamage1 \
    libxrandr2 \
    xdg-utils

# Chrome 114 sürümünü indir ve kur
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt install -y ./google-chrome-stable_current_amd64.deb && \
    rm google-chrome-stable_current_amd64.deb

# ChromeDriver 114 sürümünü indir, çıkar ve ayarla
RUN wget -q https://chromedriver.storage.googleapis.com/114.0.5735.90/chromedriver_linux64.zip && \
    unzip chromedriver_linux64.zip && \
    mv chromedriver /usr/local/bin/ && \
    chmod +x /usr/local/bin/chromedriver && \
    rm chromedriver_linux64.zip

# Çalışma dizinini ayarla
WORKDIR /app

# Maven bağımlılıklarını indir
COPY pom.xml .
RUN mvn dependency:go-offline

# Proje dosyalarını kopyala
COPY . .

# Testleri çalıştır
CMD ["mvn", "clean", "test", "-e", "-X"]
