package io.morpheus.payments.testing.architecture;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import static io.morpheus.payments.testing.architecture.ArchitecturePackages.*;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
    packages = ROOT_PACKAGE,
    importOptions = ImportOption.DoNotIncludeTests.class)
class PackageDependencyArchitectureTest {

    @ArchTest
    static final ArchRule packages_should_be_free_of_cycles =
        slices()
            .matching(ROOT_PACKAGE + ".(*)..")
            .should()
            .beFreeOfCycles();
}
