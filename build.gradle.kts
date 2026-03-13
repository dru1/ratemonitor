plugins {
    java
    id("maven-publish")
    war
    id("org.springframework.boot") version "4.0.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openrewrite.rewrite") version "7.28.1"
    id("org.cyclonedx.bom") version "3.2.2"
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Utils
    implementation("com.google.guava:guava:33.5.0-jre")
    implementation("org.jsoup:jsoup:1.22.1")

    // Databases
    runtimeOnly("com.h2database:h2:2.4.240")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client:3.5.7")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // JPA
    annotationProcessor("org.hibernate.orm:hibernate-processor")

    // Rewrite
    rewrite("org.openrewrite.recipe:rewrite-rewrite:0.21.2")
    rewrite("org.openrewrite.recipe:rewrite-spring:6.27.0")
}

group = "at.dru"
version = "0.0.1-SNAPSHOT"
description = "at.dru.ratemonitor"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
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
    projectType.set(org.cyclonedx.model.Component.Type.APPLICATION)
    includeBomSerialNumber.set(false)
    includeLicenseText.set(true)
}

rewrite {
    // activeRecipe("org.openrewrite.java.spring.boot4.UpgradeSpringBoot_4_0")
    // activeRecipe("org.openrewrite.java.recipes.RecipeNullabilityBestPractices")
    // activeRecipe("org.openrewrite.java.jspecify.JSpecifyBestPractices")
}
