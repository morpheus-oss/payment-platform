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
class PresentationArchitectureTest {

    @ArchTest
    static final ArchRule controllers_should_reside_only_in_controller_package =
        classes()
            .that()
            .haveSimpleNameEndingWith("Controller")
            .should()
            .resideInAPackage(CONTROLLER_PACKAGE);

    @ArchTest
    static final ArchRule request_models_should_reside_only_in_request_package =
        classes()
            .that()
            .haveSimpleNameEndingWith("Request")
            .should()
            .resideInAPackage(REQUEST_PACKAGE);

    @ArchTest
    static final ArchRule response_models_should_reside_only_in_response_package =
        classes()
            .that()
            .haveSimpleNameEndingWith("Response")
            .should()
            .resideInAPackage(RESPONSE_PACKAGE);

    @ArchTest
    static final ArchRule domain_should_not_depend_on_request_models =
        noClasses()
            .that()
            .resideInAPackage(DOMAIN_PACKAGE)
            .should()
            .dependOnClassesThat()
            .resideInAPackage(REQUEST_PACKAGE);

    @ArchTest
    static final ArchRule domain_should_not_depend_on_response_models =
        noClasses()
            .that()
            .resideInAPackage(DOMAIN_PACKAGE)
            .should()
            .dependOnClassesThat()
            .resideInAPackage(RESPONSE_PACKAGE);
}
