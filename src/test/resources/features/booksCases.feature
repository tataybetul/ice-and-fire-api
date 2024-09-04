Feature: booksCases

  Scenario Outline: Verify http status by book id in book response
    Given get books response with book id:<bookId>
    Then check http status with status code:"200"
    Examples:
      | bookId     |
      | "1"        |
      | "10000000" |

  Scenario Outline: Verify that character is in the book response
    Given get book response with book id:<bookId> and expected book character:<bookCharacter>
    Then verify book detail with name:<name>, isbn:<isbn>, numberOfPages:<numberOfPages> and publisher:<publisher>
    Examples:
      | bookId | bookCharacter                                            | name                | isbn             | numberOfPages | publisher      |
      | "1"    | "https://anapioficeandfire.com/api/characters/2"         | "A Game of Thrones" | "978-0553103540" | "694"         | "Bantam Books" |
      | "2"    | "https://anapioficeandfire.com/api/characters/100000000" | "A Clash of Kings"  | "978-0553108033" | "768"         | "Bantam Books" |
