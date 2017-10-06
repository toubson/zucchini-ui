package io.zucchiniui.examples

import cucumber.api.PendingException
import cucumber.api.Scenario
import cucumber.api.java8.Fr
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class ExampleEnv : Fr {

  companion object {

    lateinit var currentScenario: Scenario

  }

  init {

    Before { ->
      logger.info("Start")
    }

    Before { scenario ->
      currentScenario = scenario
    }

    Before(arrayOf("@wip")) { ->
      throw PendingException("@wip test");
    }

    After { ->
      logger.info("End")
    }

  }

}
