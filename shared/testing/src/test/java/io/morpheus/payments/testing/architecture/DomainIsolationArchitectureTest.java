package io.morpheus.payments.testing.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import static io.morpheus.payments.testing.architecture.ArchitecturePackages.DOMAIN_PACKAGE;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
    packages = "io.morpheus.payments",
    importOptions = ImportOption.DoNotIncludeTests.class)
class DomainIsolationArchitectureTest   {

	@ArchTest
	static final ArchRule domain_should_not_depend_on_spring =
        noClasses()
            .that()
            .resideInAPackage(DOMAIN_PACKAGE)
            .should()
			.dependOnClassesThat()
            .resideInAnyPackage("org.springframework..");

	@ArchTest
	static final ArchRule domain_should_not_depend_on_jakarta =
        noClasses()
            .that()
            .resideInAPackage(DOMAIN_PACKAGE)
            .should()
			.dependOnClassesThat()
            .resideInAnyPackage("jakarta..");

	@ArchTest
	static final ArchRule domain_should_not_depend_on_jpa =
        noClasses()
            .that()
            .resideInAPackage(DOMAIN_PACKAGE)
            .should()
			.dependOnClassesThat()
            .resideInAnyPackage("javax.persistence..");

	@ArchTest
	static final ArchRule domain_should_not_depend_on_web =
        noClasses()
            .that()
            .resideInAPackage(DOMAIN_PACKAGE)
            .should()
			.dependOnClassesThat()
            .resideInAnyPackage(
                "org.springframework.web..",
                "org.springframework.http..");

	@ArchTest
	static final ArchRule domain_should_not_depend_on_messaging =
        noClasses()
            .that()
            .resideInAPackage(DOMAIN_PACKAGE)
			.should()
            .dependOnClassesThat()
            .resideInAnyPackage(
                "org.springframework.amqp..",
                "org.springframework.kafka..");
}
