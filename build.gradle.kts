plugins {
    id("java")
    id("maven-publish")
    signing
}

group = providers.gradleProperty("package.group").get()
version = providers.gradleProperty("package.version").get()

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    withJavadocJar()
    withSourcesJar()
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
                description = providers.gradleProperty("pom.description").get()
                url = providers.gradleProperty("pom.scm.url").get()

                licenses {
                    license {
                        name = providers.gradleProperty("pom.license.name").get()
                        url = providers.gradleProperty("pom.license.url").get()
                    }
                }

                developers {
                    developer {
                        id = providers.gradleProperty("pom.developer.id").get()
                        name = providers.gradleProperty("pom.developer.name").get()
                        email = providers.gradleProperty("pom.developer.email").get()
                    }
                }

                scm {
                    connection = providers.gradleProperty("pom.scm.connection").get()
                    developerConnection = providers.gradleProperty("pom.scm.developerConnection").get()
                    url = providers.gradleProperty("pom.scm.url").get()
                }

            }
        }
    }

    repositories {
        maven {

            val repoProperties = if (version.toString().endsWith("SNAPSHOT")) "snapshot" else "release"
            url = uri(providers.gradleProperty("package.${repoProperties}.repo").get())

            credentials {

            }
        }
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}

task("hello") {
    println(findProperty("sonaUsername"))
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

tasks.test {
    useJUnitPlatform()
}