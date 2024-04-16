LocalDate comparisonDate = LocalDate.parse("2001-11-09");

        JSONObject obj = new JSONObject(jsonString);
        if (obj.has("transactions")) {
            JSONArray transactions = obj.getJSONArray("transactions");

            for (int i = 0; i < transactions.length(); i++) {
                JSONObject transaction = transactions.getJSONObject(i);

                if (transaction.has("startingActions") && transaction.has("suspiciousTransactionDetails")) {
                    JSONArray startingActions = transaction.getJSONArray("startingActions");
                    JSONObject suspiciousTransactionDetails = transaction.getJSONObject("suspiciousTransactionDetails");

                    if (suspiciousTransactionDetails.has("attemptedTransactionIndicator")) {
                        boolean attemptedTransactionIndicator = suspiciousTransactionDetails.getBoolean("attemptedTransactionIndicator");

                        if (startingActions.isEmpty() && !attemptedTransactionIndicator) {
                            System.out.println("transactions[" + i + "].startingActions");
                            System.out.println("transactions[" + i + "].suspiciousTransactionDetails.attemptedTransactionIndicator");
                        }
                    }

                    if (suspiciousTransactionDetails.has("dateOfPosting")) {
                        LocalDate dateOfPosting = LocalDate.parse(suspiciousTransactionDetails.getString("dateOfPosting"));

                        if (dateOfPosting.isBefore(comparisonDate)) {
                            System.out.println("transactions[" + i + "].suspiciousTransactionDetails.dateOfPosting");
                        }
                    }

                    if (suspiciousTransactionDetails.has("dateOfTransaction")) {
                        LocalDate dateOfTransaction = LocalDate.parse(suspiciousTransactionDetails.getString("dateOfTransaction"));

                        if (dateOfTransaction.isBefore(comparisonDate)) {
                            System.out.println("transactions[" + i + "].suspiciousTransactionDetails.dateOfTransaction");
                        }
                    }
                }
            }
        }
