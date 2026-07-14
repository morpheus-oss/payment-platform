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
class PackageResidencyArchitectureTest {

    @ArchTest
    static final ArchRule use_cases_should_reside_in_use_case_package =
        classes()
            .that()
            .haveSimpleNameEndingWith("UseCase")
            .should()
            .resideInAPackage(APPLICATION_USE_CASE);

    @ArchTest
    static final ArchRule ports_should_reside_in_port_package =
        classes()
            .that()
            .haveSimpleNameEndingWith("Port")
            .should()
            .resideInAPackage(APPLICATION_PORT);

    @ArchTest
    static final ArchRule controllers_should_reside_in_controller_package =
        classes()
            .that()
            .haveSimpleNameEndingWith("Controller")
            .should()
            .resideInAPackage(CONTROLLER_PACKAGE);

    @ArchTest
    static final ArchRule adapters_should_reside_in_adapter_packages =
        classes()
            .that()
            .haveSimpleNameEndingWith("Adapter")
            .should()
            .resideInAnyPackage(
                PERSISTENCE_PACKAGE,
                MESSAGING_PACKAGE);
}
