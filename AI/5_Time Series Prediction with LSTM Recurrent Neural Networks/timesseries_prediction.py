# -*- coding: utf-8 -*-

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import torch
import torch.nn as nn
import torch.optim as optim
import torch.utils.data as data

# Load the dataset
df = pd.read_csv('airline-passengers.csv')
timeseries = np.array(df['Passengers'], dtype=np.float32)

# Split the data into training and testing sets
train_size = int(len(timeseries) * 0.67)
test_size = len(timeseries) - train_size
train, test = timeseries[:train_size], timeseries[train_size:]

# Function to create input-output pairs for time series prediction
def create_dataset(dataset, lookback):
    X, y = [], []
    for i in range(len(dataset)-lookback):
        feature = dataset[i:i+lookback]
        target = dataset[i+1:i+lookback+1]
        X.append(feature)
        y.append(target)
    X = np.array(X)
    y = np.array(y)
    return torch.tensor(X), torch.tensor(y)

# Set the lookback window size
lookback = 4

# Create training and testing datasets using the create_dataset function
X_train, y_train = create_dataset(train, lookback=lookback)
X_test, y_test = create_dataset(test, lookback=lookback)

# # Define the LSTM model
# class AirModel(nn.Module):
#     def __init__(self, hidden_size, num_layers):
#         super().__init__()
#         self.lstm = nn.LSTM(input_size=1, hidden_size=hidden_size, num_layers=num_layers, batch_first=True)
#         self.linear = nn.Linear(hidden_size, 1)
#
#     def forward(self, x):
#         x, _ = self.lstm(x)
#         x = self.linear(x)
#         return x

# Set hyperparameters
batch_size = 8
hidden_size = 512
num_layers = 1
learning_rate = 0.005
dropout_rates = 0.4
n_epochs = 1500

class SimpleAirModel(nn.Module):
    def __init__(self, hidden_size, num_layers):
        super().__init__()
        self.rnn = nn.RNN(input_size=1, hidden_size=hidden_size, num_layers=num_layers, batch_first=True)
        self.linear = nn.Linear(hidden_size, 1)

    def forward(self, x):
        x, _ = self.rnn(x)
        x = self.linear(x)
        return x


# Instantiate the model, define the loss function, and set up the optimizer
model = SimpleAirModel(hidden_size, num_layers)
optimizer = optim.Adam(model.parameters(), lr=learning_rate)
loss_fn = nn.MSELoss()
loader = data.DataLoader(data.TensorDataset(X_train, y_train), shuffle=True, batch_size=batch_size)

# Training loop
for epoch in range(n_epochs):
    model.train()
    for X_batch, y_batch in loader:
        # Adjust the shape of input for LSTM
        n1, n2 = X_batch.shape
        X_batch = X_batch.view(n1, n2, 1)
        y_pred = model(X_batch)
        y_pred = y_pred.view(n1, n2)
        loss = loss_fn(y_pred, y_batch)
        optimizer.zero_grad()
        loss.backward()
        optimizer.step()

    # Validation
    if epoch % 100 != 0:
        continue
    model.eval()
    with torch.no_grad():
        # Evaluate on the training set
        X_batch = X_train
        n1, n2 = X_batch.shape
        X_batch = X_batch.view(n1, n2, 1)
        y_pred = model(X_batch)
        y_pred = y_pred.view(n1, n2)
        train_rmse = np.sqrt(loss_fn(y_pred, y_train))

        # Evaluate on the testing set
        X_batch = X_test
        n1, n2 = X_batch.shape
        X_batch = X_batch.view(n1, n2, 1)
        y_pred = model(X_batch)
        y_pred = y_pred.view(n1, n2)
        test_rmse = np.sqrt(loss_fn(y_pred, y_test))

    print(f"Epoch {epoch}: train RMSE {train_rmse.item():.4f}, test RMSE {test_rmse.item():.4f}")


with torch.no_grad():
    model.eval()
    X_test_tensor = torch.tensor(test).view(1, len(test), 1)
    y_pred = model(X_test_tensor)
    y_pred = y_pred.view(len(test)).cpu().numpy()

    X_batch = X_test
    n1, n2 = X_batch.shape
    X_batch = X_batch.view(n1, n2, 1)
    y_pred_batch = model(X_batch)
    y_pred_batch = y_pred_batch.view(n1, n2)

# Plot the results
y_true = test[lookback:]
yp1_batch = y_pred_batch.cpu().numpy()[:, -1]

plt.figure(1)
plt.plot(y_true, 'r', label='True Data')
plt.plot(yp1_batch, 'b', label='Predicted Data')
plt.legend()
plt.show()

