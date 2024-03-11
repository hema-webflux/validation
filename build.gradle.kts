plugins {
    id("java")
    id("maven-publish")
    signing
}

apply(from = findProperty("SIGNING_CONFIGURE"))

group = findProperty("PACKAGE_GROUP") as String
version = findProperty("PACKAGE_VERSION") as String

repositories {
    mavenCentral()
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {

            groupId = "${project.group}"
            artifactId = project.name
            version = "${project.version}"

            from(components["java"])

            pom {
                name = project.name
                description = property("POM_DESCRIPTION") as String
                url = findProperty("POM_SCM_URL") as String

                licenses {
                    license {
                        name = findProperty("POM_LICENSE_NAME") as String
                        url = findProperty("POM_LICENSE_URL") as String
                    }
                }

                developers {
                    developer {
                        id = findProperty("POM_DEVELOPER_ID") as String
                        name = findProperty("POM_DEVELOPER_NAME") as String
                        email = findProperty("POM_DEVELOPER_EMAIL") as String
                    }
                }

                scm {
                    connection = findProperty("POM_SCM_CONNECTION") as String
                    developerConnection = findProperty("POM_SCM_DEVELOPER_CONNECTION") as String
                    url = findProperty("POM_SCM_URL") as String
                }

            }
        }
    }

    repositories {
        maven {

            val isSnapshot = version.toString().endsWith("SNAPSHOT")

            val repositories: String = if (isSnapshot) "SNAPSHOT" else "RELEASE"

            url = uri(findProperty("NEXUS_${repositories}_URL") as String)

            credentials {
                username = project.ext.get("sonaUsername").toString()
                password = project.ext.get("sonaPassword").toString()
            }
        }
    }
}

dependencies {
    implementation("org.json:json:20231013")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.3")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("io.github.hema-webflux:contracts:1.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

signing {
    sign(publishing.publications["mavenJava"])
}