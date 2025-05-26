
# 🛒 E-Ticaret UI Otomasyon Test Projesi (Java + Cucumber + Selenium)

Bu proje, bir e-ticaret platformunda kullanıcı girişi yaparak ürün arama, filtreleme ve sepete ürün ekleme gibi işlemleri UI otomasyon testleri ile gerçekleştirmektedir. Testler, **BDD (Behavior Driven Development)** yaklaşımıyla **Java + Cucumber** kullanılarak yazılmıştır.

## 🎯 Test Senaryosu Özeti

1. Kullanıcı e-ticaret sitesini ziyaret eder.
2. Giriş işlemi gerçekleştirilir.
3. "Cep telefonu" araması yapılır.
4. Fiyat aralığı **15.000 – 20.000 TL** olarak filtrelenir.
5. Filtrelenen sonuçlarda en alt sıradaki rastgele ürün seçilir.
6. Ürün detayından en düşük puanlı satıcının ürünü sepete eklenir.
7. Ürünün sepete eklendiği doğrulanır.

## 🔧 Özellikler

- 🧪 **BDD** ile senaryo yazımı (Cucumber & Gherkin)
- 🌐 **Parametrik tarayıcı desteği** (Chrome, Firefox)
- 📊 **Cucumber Reports** ile test sonucu raporlaması
- 🖼️ **Hata durumlarında ekran görüntüsü alma** (Hooks ile)
- 🐳 **Docker Image** & Docker Compose ile çalıştırılabilir yapı 
- ⚡ **Paralel test çalıştırma desteği** 
- mvn clean test -Dcucumber.filter.tags="@login or @addtocart" -Dcucumber.execution.parallel.enabled=true -Dcucumber.execution.parallel.config.strategy=fixed -Dcucumber.execution.parallel.config.fixed.parallelism=4

## 🧰 Teknolojiler

- Java 
- Selenium WebDriver
- Cucumber (BDD)
- JUnit + Maven Surefire Plugin
- Cucumber Reports
- Docker & Docker Compose

## 🚀 Kurulum

### 1. Gereksinimler

- Java 
- Maven 3.x
- Git
- Docker 

### 2. Projeyi Klonlayın

```bash
https://github.com/HilmiOzcelikqa/JavaCucunmberBDD.git

```

### 3. Testleri Çalıştırın

#### Maven ile (lokal):

```bash
mvn clean test
```

#### Tarayıcı seçerek çalıştırma:

```bash
mvn clean test -Dbrowser=chrome
mvn clean test -Dbrowser=firefox
Config dosyasında browser=chrome or firefox
Config dosyasın görsel otomasyon için headless=false yapılmadılar.
Docker için headless=false olarak bırakılmıştır.
```

### 4. Raporları Görüntüleme

Test sonrası raporlar aşağıdaki dizinlerde oluşur:

- `target/cucumber-reports` (HTML formatında)
- `target/surefire-reports` (JUnit XML raporları)

### 5. Docker ile Çalıştırma

Docker yüklü sistemlerde aşağıdaki komutlarla testleri container içinde çalıştırabilirsiniz:

```bash
docker pull 240315/assesment-bdd:v2

docker run 240315/assesment-bdd:v2
```

> 🐳 DockerHub Link: [https://hub.docker.com/r/240315/assesment-bdd](https://hub.docker.com/r/240315/assesment-bdd)

### 6. Docker Compose ile Çalıştırma

```bash
docker-compose up --build
```

## ⚠️ Dikkat Edilmesi Gerekenler

- Testlerin çalışabilmesi için kullanıcı adı ve şifre değerleri şifrelenmiş olmalıdır.
- `Encryptor` sınıfı yardımıyla bu işlem bir kez yapılır ve şifrelenmiş çıktılar kullanılır.
- Güvenlik için şifreleme işlemi tamamlandıktan sonra `Encryptor.java` sınıfı projeden kaldırılabilir.

## 📌 Notlar

- Cucumber ve Maven Surefire ile tam raporlama desteği sağlanmıştır.Docker desteği eklenmiştir.

## 📬 İletişim

hilmiozcelikqa@gmail.com
DockerHub:(https://hub.docker.com/u/240315)
