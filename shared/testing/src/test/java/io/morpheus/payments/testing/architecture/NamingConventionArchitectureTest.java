package io.morpheus.payments.testing.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import static io.morpheus.payments.testing.architecture.ArchitecturePackages.*;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
    packages = ROOT_PACKAGE,
    importOptions = ImportOption.DoNotIncludeTests.class)
class NamingConventionArchitectureTest {

    @ArchTest
    static final ArchRule controllers_should_be_named_controller =
        classes()
            .that()
            .resideInAPackage(CONTROLLER_PACKAGE)
            .should()
            .haveSimpleNameEndingWith("Controller");

    @ArchTest
    static final ArchRule use_cases_should_be_named_use_case =
        classes()
            .that()
            .resideInAPackage(APPLICATION_USE_CASE)
            .and()
            .haveSimpleNameEndingWith("UseCase")
            .should()
            .haveSimpleNameEndingWith("UseCase");

    @ArchTest
    static final ArchRule ports_should_be_named_port =
        classes()
            .that()
            .resideInAPackage(APPLICATION_PORT)
            .should()
            .haveSimpleNameEndingWith("Port");

    @ArchTest
    static final ArchRule adapters_should_be_named_adapter =
        classes()
            .that()
            .resideInAnyPackage(
                PERSISTENCE_PACKAGE,
                MESSAGING_PACKAGE)
            .and()
            .haveSimpleNameEndingWith("Adapter")
            .should()
            .haveSimpleNameEndingWith("Adapter");
}
