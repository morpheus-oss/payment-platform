package io.morpheus.payments.testing.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import static io.morpheus.payments.testing.architecture.ArchitecturePackages.*;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
    packages = ROOT_PACKAGE,
    importOptions = ImportOption.DoNotIncludeTests.class)
class AdapterArchitectureTest {

    @ArchTest
    static final ArchRule persistence_should_not_depend_on_web =
        noClasses()
            .that()
            .resideInAPackage(PERSISTENCE_PACKAGE)
            .should()
            .dependOnClassesThat()
            .resideInAPackage(CONTROLLER_PACKAGE);

    @ArchTest
    static final ArchRule messaging_should_not_depend_on_web =
        noClasses()
            .that()
            .resideInAPackage(MESSAGING_PACKAGE)
            .should()
            .dependOnClassesThat()
            .resideInAPackage(CONTROLLER_PACKAGE);

    @ArchTest
    static final ArchRule persistence_should_not_depend_on_messaging =
        noClasses()
            .that()
            .resideInAPackage(PERSISTENCE_PACKAGE)
            .should()
            .dependOnClassesThat()
            .resideInAPackage(MESSAGING_PACKAGE);

    @ArchTest
    static final ArchRule messaging_should_not_depend_on_persistence =
        noClasses()
            .that()
            .resideInAPackage(MESSAGING_PACKAGE)
            .should()
            .dependOnClassesThat()
            .resideInAPackage(PERSISTENCE_PACKAGE);
}
