plugins {
    java
    id("maven-publish")
    war
    id("org.springframework.boot") version "2.4.3"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.spring.io/libs-release")
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web:2.4.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.4.3")

    // Databases
    implementation("org.hsqldb:hsqldb:2.5.1")
    implementation("mysql:mysql-connector-java:8.0.23")

    // Utils
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("com.google.guava:guava:28.2-jre")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.jsoup:jsoup:1.13.1")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.4.3")
    testImplementation("org.dbunit:dbunit:2.7.0")
    testImplementation("com.github.springtestdbunit:spring-test-dbunit:1.3.0")

    // Spring Boot
    providedCompile("org.springframework.boot:spring-boot-starter-tomcat:2.4.3")

    // JPA
    annotationProcessor("org.hibernate:hibernate-jpamodelgen:5.4.28.Final")
}

group = "at.dru"
version = "0.0.1-SNAPSHOT"
description = "at.dru.ratemonitor"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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

tasks.bootRun {
    main = "at.dru.ratemonitor.Application"
}
