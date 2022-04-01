# Maybe Data

Maybe Data is a super simple, lightweight library that allows you to conditionally load data. 

Maybe Data will *only* attempt to load when the mod is installed (= a mod bundles Maybe Data), and when the condition at the top of the recipe file is met.

### Recipes

`resources/data/modid/mayberecipes/golden_apple.json`
```json
{
  "condition": {
    "modid": "moreapples"
  },
  "recipe": {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "###",
      "#X#",
      "###"
    ],
    "key": {
      "#": {
        "item": "minecraft:diamond"
      },
      "X": {
        "item": "minecraft:apple"
      }
    },
    "result": {
      "item": "minecraft:golden_apple"
    }
  }
}
```

This recipe will only load when Maybe Data is installed (More Apples / another mod might JIJ it), and when the `moreapples` modid exists. Simple, right?

### Loot Tables

Add the following json block to the top level of your loot table .json file (in the same directory as vanilla):
```json
"condition": {
    "modid": "testmod"
}
```

Example:
```json
{
  "condition": {
    "modid": "testmod"
  },
  "type": "minecraft:block",
  // ... rest of loot table here
}
```

If the required modid is not present when datapacks are loaded, the loot table will be skipped.

---

Looking for more conditional control? Check out [LibCD](https://github.com/cottonmc/LibCD). This mod is MIT and built for the Fabric Loader.
