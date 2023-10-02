import kotlin.random.Random

class DiceRollerV2 {

    // сделайте интерфейс OnRollCallback функциональным
    fun interface OnRollCallback {
        fun call(firstDiceValue: Int, secondDiceValue: Int)
    }

    // сделайте интерфейс OnDoubleCallback функциональным
    fun interface OnDoubleCallback {
        fun call(diceValue: Int)
    }

    private var onRollCallback: OnRollCallback? = null
    private var onDoubleCallback: OnDoubleCallback? = null

    fun setCallbacks(onRollCallback: OnRollCallback, onDoubleCallback: OnDoubleCallback) {
        this.onRollCallback = onRollCallback
        this.onDoubleCallback = onDoubleCallback
    }

    fun roll() {
        if (onRollCallback == null || onDoubleCallback == null) {
            println("Вы должны вызвать функцию setCallbacks() прежде чем бросать кубики")
            return
        }

        val firstDiceValue = Random.nextInt(1, 7)
        val secondDiceValue = Random.nextInt(1, 7)

        if (firstDiceValue != secondDiceValue) {
            onRollCallback?.call(firstDiceValue, secondDiceValue)
        } else {
            onDoubleCallback?.call(firstDiceValue)
        }
    }
}

fun main() {
    val diceRoller = DiceRollerV2()

    diceRoller.setCallbacks(
        // передайте лямбду в качестве OnRollCallback
        OnRollCallback { firstDiceValue: Int, secondDiceValue: Int ->
            println("На кубиках выпало $firstDiceValue и $secondDiceValue")
        },
        // передайте лямбду в качестве OnDoubleCallback
        OnDoubleCallback { diceValue: Int ->
            println("Ура!!! Дубль на $diceValue-ах! Бросаем ещё раз")
            diceRoller.roll()
        }
    )

    repeat(10) {
        diceRoller.roll()
    }
}
