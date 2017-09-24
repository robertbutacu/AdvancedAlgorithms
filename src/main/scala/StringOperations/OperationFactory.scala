package StringOperations

/**
  * Created by Robert-PC on 9/21/2017.
  */
trait OperationFactory {
  def compute(x: Option[String], operation: Operation, y: Option[String]): Option[String] = {
    isValid(x, y, operation) match {
      case Valid                 => handleComputation(x.get, y.get, operation)
      case InvalidInputException => None
    }
  }

  private def handleComputation(x: String, y: String, operation: Operation): Option[String] = {
    getSignums(x, y) match {
      case NoNegativeOperands    =>
        if(y > x && operation == Subtract)
          Some("-" ++ Subtraction(y, x))
        else Some(executeComputation(x, y, operation))

      case NegativeLeftOperand   =>
        handleNegativeLeftOperand(x.drop(1), y, operation)

      case NegativeRightOperand  =>
        handleNegativeRightOperand(x, y.drop(1), operation)

      case BothOperandsNegative  =>
        handleBothOperandsNegative(x.drop(1), y.drop(1), operation)
    }
  }

  private def executeComputation(x: String, y: String, operation: Operation): String = {
    operation match {
      case Add      => Addition(x, y)
      case Multiply => Multiplication(x, y)
      case Subtract => Subtraction(x, y)
      case Pow      => Exponentiation(x, y)
      case Divide   => "Unknown"
    }
  }

  private def handleBothOperandsNegative(x: String, y: String, operation: Operation): Option[String] = {
    operation match {
      case Add      =>
        Some("-" ++ Addition(x, y))
      case Subtract =>
        if(x > y) Some("-" ++ Subtraction(x, y))
        else      Some(Subtraction(y, x))
      case Multiply =>
        Some(Multiplication(x, y))
      case Divide   =>
        if ( y.drop(1).dropWhile(_.equals('0')).isEmpty)
          None
        else
          Some(Division(x, y))
      case Pow      =>
        None
    }
  }

  private def handleNegativeLeftOperand(x: String, y: String, operation: Operation): Option[String] = {
    operation match {
      case Add      =>
        if (x > y) Some("-" ++ Subtraction(x, y))
        else Some(Subtraction(y, x))
      case Subtract => Some("-" ++ Addition(x, y))
      case Multiply => Some("-" ++ Multiplication(x, y))
      case Divide   =>
        if ( y.dropWhile(_.equals('0')).isEmpty )
          None
        else
          Some("-" ++ Division(x, y))
      case Pow      => None
    }
  }

  private def handleNegativeRightOperand(x: String, y: String, operation: Operation): Option[String] = {
    operation match {
      case Add      =>
        if(x > y) Some(Subtraction(x, y))
        else      Some("-" ++ Subtraction(y, x))
      case Subtract => Some(Addition(x, y))
      case Multiply => Some("-" ++ Multiplication(x, y))
      case Divide   => None
      case Pow      => None
    }
  }

  private def getSignums(x: String, y: String): Signum = {
    (x.charAt(0), y.charAt(0)) match {
      case ('-', '-') => BothOperandsNegative
      case ('-', _)   => NegativeLeftOperand
      case (_, '-')   => NegativeRightOperand
      case _          => NoNegativeOperands
    }
  }

  private def isDigitsOnly(x: Option[String], y: Option[String]): Boolean = {
    x.get.filter(!_.equals('-')).forall(_.isDigit) &&
      y.get.filter(!_.equals('-')).forall(_.isDigit)
  }

  private def isSignumCorrect(x: String, operation: Operation): Boolean = {
    x.forall(_.isDigit) ||
      (
        x.startsWith("-") &&
        (x.count(_.equals('-')) == 1) &&
          operation != Pow
        )
  }

  private def areDefined(x: Option[String], y: Option[String]): Boolean = {
    x.isDefined && y.isDefined
  }

  private def isValid(x: Option[String], y: Option[String], op: Operation): InputException = {
    if(areDefined(x, y) && isDigitsOnly(x, y)) {
      if (isSignumCorrect(x.get, op) && isSignumCorrect(y.get, op))
        Valid
      else
        InvalidInputException
    }
    else
      InvalidInputException
  }
}
