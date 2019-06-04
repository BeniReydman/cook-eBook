package com.cook_ebook.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Import the individual test files
import com.cook_ebook.tests.business.InitBusinessTest;
import com.cook_ebook.tests.objects.InitObjectTest;

import com.cook_ebook.tests.objects.RecipeTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        InitBusinessTest.class,
        InitObjectTest.class,
        RecipeTest.class
})

public class AllTests
{

}