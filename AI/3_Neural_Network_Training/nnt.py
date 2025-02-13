from sklearn.neural_network import MLPClassifier
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import classification_report
from sklearn.model_selection import GridSearchCV
import pandas as pd
import numpy as np

# Loading the data
dataset = pd.read_csv("accent-mfcc-data-1.csv")

# Splitting into features and labels
X = dataset.drop('language', axis=1)
y = dataset['language']

# Split into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Normalizing the data
scaler = StandardScaler()
X_train = scaler.fit_transform(X_train)
X_test = scaler.transform(X_test)

# Defining the parameters to be checked
param_grid = {
    'learning_rate_init': [0.001, 0.01, 0.1],
    'batch_size': [32, 64, 128]
}

# Creating MLP
mlp = MLPClassifier(hidden_layer_sizes=(100, 50), max_iter=500, activation='relu', random_state=42)

# Model training
mlp.fit(X_train, y_train)

# Evaluate initial model results
y_pred_initial = mlp.predict(X_test)
print("Initial Model Results:")
print(classification_report(y_test, y_pred_initial))

# Use GridSearchCV to explore different parameter combinations
grid_search = GridSearchCV(mlp, param_grid, cv=3, scoring='accuracy')
grid_search.fit(X_train, y_train)

# Print the best parameters
print("Best parameters:", grid_search.best_params_)

# Updating the model with the best parameters
best_mlp = grid_search.best_estimator_

# Train the model with the best hyperparameters
best_mlp.fit(X_train, y_train)

# Evaluate the final model results
y_pred_final = best_mlp.predict(X_test)
print("Final Model Results:")
print(classification_report(y_test, y_pred_final))

# Save the weights of the trained network
weights_path = "trained_weights.npy"
flattened_weights = np.concatenate([layer.flatten() for layer in best_mlp.coefs_])
np.save(weights_path, flattened_weights)

print(f"Weights saved to {weights_path}")
