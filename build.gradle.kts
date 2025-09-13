plugins {
    java
    id("maven-publish")
    war
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.cyclonedx.bom") version "2.3.1"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.spring.io/libs-release")
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Utils
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("com.google.guava:guava:33.4.8-jre")
    implementation("org.jsoup:jsoup:1.21.2")

    // Databases
    runtimeOnly("com.h2database:h2:2.3.232")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client:3.5.6")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.dbunit:dbunit:2.8.0")
    testImplementation("com.github.springtestdbunit:spring-test-dbunit:1.3.0")

    // Spring Boot
    providedCompile("org.springframework.boot:spring-boot-starter-tomcat")

    // JPA
    annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen")
}

group = "at.dru"
version = "0.0.1-SNAPSHOT"
description = "at.dru.ratemonitor"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["web"])
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootRun {
    mainClass.set("at.dru.ratemonitor.Application")
}

tasks.cyclonedxBom {
    setProjectType("application")
    setOutputName("bom")
    setOutputFormat("json")
    setIncludeBomSerialNumber(false)
    setIncludeLicenseText(true)
    setComponentVersion("2.0.0")
}
