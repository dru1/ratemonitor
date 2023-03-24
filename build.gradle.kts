plugins {
    java
    id("maven-publish")
    war
    id("org.springframework.boot") version "2.7.6"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.cyclonedx.bom") version "1.7.4"
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
    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.jsoup:jsoup:1.15.3")

    // Databases
    runtimeOnly("org.hsqldb:hsqldb:2.5.2")
    runtimeOnly("mysql:mysql-connector-java:8.0.30")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.dbunit:dbunit:2.7.3")
    testImplementation("com.github.springtestdbunit:spring-test-dbunit:1.3.0")

    // Spring Boot
    providedCompile("org.springframework.boot:spring-boot-starter-tomcat")

    // JPA
    annotationProcessor("org.hibernate:hibernate-jpamodelgen")
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
