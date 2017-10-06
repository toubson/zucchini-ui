package io.zucchiniui.examples

import com.google.common.io.Resources
import cucumber.api.DataTable
import cucumber.api.PendingException
import cucumber.api.java8.Fr
import io.zucchiniui.examples.ExampleEnv.Companion.currentScenario
import org.assertj.core.api.Assertions

class ExampleSteps : Fr {

  var calcul: Int? = null

  init {

    Soit("^un contexte initialisé$") { ->
      // OK !
    }

    Quand("^j'additionne (-?[0-9]+) et (-?[0-9]+)$") { a: Int, b: Int ->
      calcul = a + b
    }

    Alors("^j'obtiens (-?[0-9]+)$") { resultat: Int ->
      currentScenario.write("S'assurer que le calcul renvoie bien ${resultat}")
      Assertions.assertThat(calcul).isEqualTo(resultat)
    }

    Alors("^j'obtiens le tableau suivant:$") { expectedTable: DataTable ->
      expectedTable.diff(listOf(
        listOf(1, 2, 3),
        listOf(4, 5, 6)
      ))
    }

    Soit("^une tâche en attente$") { ->
      throw PendingException()
    }

    Quand("^j'affiche l'image de Grumpy Cat$") { ->
      val data = Resources.toByteArray(Resources.getResource("attachments/grumpy-cat.jpg"))
      currentScenario.embed(data, "image/jpeg")
    }

    Alors("^je suis de mauvaise humeur$") { ->
      // OK !
    }

  }

}
