LocalDate comparisonDate = LocalDate.parse("2001-11-09");

if (element.isJsonArray() && element.getAsJsonArray().size() == 0 && !path.isEmpty()) {
        element.getAsJsonArray().add(new JsonObject());
    }
