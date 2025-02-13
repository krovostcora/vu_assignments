from sklearn.cluster import KMeans, AgglomerativeClustering
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.colors as mcolors
from sklearn import model_selection
from sklearn.decomposition import PCA
from sklearn.preprocessing import StandardScaler

data = pd.read_csv('accent-mfcc-data-1.csv')
X = data.drop('language', axis=1)
y = data['language']

# Data split
X_train, X_test, y_train, y_test = model_selection.train_test_split(X, y, test_size=0.2, random_state=1)

# Scaling
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# KMeans Clustering
kmeans = KMeans(n_clusters=20)
kmeans.fit(X_train_scaled)
labels_train = kmeans.labels_
labels_test = kmeans.predict(X_test_scaled)

# DataFrame
df = pd.DataFrame({'Class': y_test, 'Cluster': labels_test})
cluster_counts = df.groupby(['Cluster', 'Class']).size().unstack(fill_value=0)

print(cluster_counts)


# Agglomerative Clustering
agg_clustering = AgglomerativeClustering(n_clusters=10, linkage='ward')
agg_labels_train = agg_clustering.fit_predict(X_train_scaled)
agg_labels_test = agg_clustering.fit_predict(X_test_scaled)


# DataFrame
df_agg = pd.DataFrame({'Class': y_test, 'Cluster': agg_labels_test})
cluster_counts_agg = df_agg.groupby(['Cluster', 'Class']).size().unstack(fill_value=0)

print("Agglomerative Clustering Results:")
print(cluster_counts_agg)


# Visualize results using PCA
pca = PCA(n_components=2)
X_train_pca = pca.fit_transform(X_train_scaled)
X_test_pca = pca.transform(X_test_scaled)

# Plot KMeans Clustering
plt.figure(figsize=(12, 6))
plt.subplot(1, 2, 1)
plt.scatter(X_train_pca[:, 0], X_train_pca[:, 1], c=labels_train, cmap='magma')
plt.title('KMeans Clustering - Training Data')

plt.subplot(1, 2, 2)
plt.scatter(X_test_pca[:, 0], X_test_pca[:, 1], c=labels_test, cmap='magma')
plt.title('KMeans Clustering - Test Data')

plt.show()

# Plot Agglomerative Clustering
plt.figure(figsize=(12, 6))
plt.subplot(1, 2, 1)
plt.scatter(X_train_pca[:, 0], X_train_pca[:, 1], c=agg_labels_train, cmap='viridis')
plt.title('Agglomerative Clustering - Training Data')

plt.subplot(1, 2, 2)
plt.scatter(X_test_pca[:, 0], X_test_pca[:, 1], c=agg_labels_test, cmap='viridis')
plt.title('Agglomerative Clustering - Test Data')

plt.show()