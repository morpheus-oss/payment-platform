package io.morpheus.payments.testing.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import static io.morpheus.payments.testing.architecture.ArchitecturePackages.*;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
    packages = ROOT_PACKAGE,
    importOptions = ImportOption.DoNotIncludeTests.class)
class ApplicationLayerArchitectureTest {

    @ArchTest
    static final ArchRule application_should_not_depend_on_controllers =
        noClasses()
            .that()
            .resideInAPackage(APPLICATION_PACKAGE)
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(CONTROLLER_PACKAGE);

    @ArchTest
    static final ArchRule application_should_not_depend_on_persistence_adapters =
        noClasses()
            .that()
            .resideInAPackage(APPLICATION_PACKAGE)
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(PERSISTENCE_PACKAGE);

    @ArchTest
    static final ArchRule application_should_not_depend_on_messaging_adapters =
        noClasses()
            .that()
            .resideInAPackage(APPLICATION_PACKAGE)
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(MESSAGING_PACKAGE);

    @ArchTest
    static final ArchRule application_should_not_depend_on_configuration =
        noClasses()
            .that()
            .resideInAPackage(APPLICATION_PACKAGE)
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(CONFIG_PACKAGE);
/*
    @ArchTest
    static final ArchRule use_cases_should_implement_usecase_interface =
        classes()
            .that()
            .resideInAPackage(APPLICATION_USE_CASE)
            .and()
            .haveSimpleNameEndingWith("UseCase")
            .should()
            .implement(
                io.morpheus.payments.payment.application.usecase.UseCase.class);

 */

}
