plugins {
    id("java")
    id("maven-publish")
    signing
}

//apply(from = providers.gradleProperty("package.publish.settings").get())

group = providers.gradleProperty("package.group").get()
version = providers.gradleProperty("package.version").get()

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "${project.group}"
            artifactId = project.name
            version = "${project.version}"

            from(components["java"])
        }
        create<MavenPublication>("mavenJava") {
            pom {
                name = project.name
                description = providers.gradleProperty("package.description").get()
                url = providers.gradleProperty("package.scm.url").get()

                licenses {
                    license {
                        name = providers.gradleProperty("package.license.name").get()
                        url = providers.gradleProperty("package.license.url").get()
                    }
                }

                developers {
                    developer {
                        id = providers.gradleProperty("package.developer.id").get()
                        name = providers.gradleProperty("package.developer.name").get()
                        email = providers.gradleProperty("package.developer.email").get()
                    }
                }

                scm {
                    connection = providers.gradleProperty("package.scm.connection").get()
                    developerConnection = providers.gradleProperty("package.scm.developerConnection").get()
                    url = providers.gradleProperty("package.scm.url").get()
                }

            }
        }
    }
    repositories {
        maven {

            url = uri(providers.gradleProperty("package.publish.url").get())

            credentials{
                username
            }
        }
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

signing {
    sign(publishing.publications["mavenJava"])
}

task("hello") {
    println("sonatypeUsername")
}

tasks.test {
    useJUnitPlatform()
}