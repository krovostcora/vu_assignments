from sklearn import metrics
from sklearn import model_selection
from sklearn import tree
from sklearn.preprocessing import StandardScaler
import pandas as pd

data = pd.read_csv('accent-mfcc-data-1.csv')

X = data.drop('language', axis=1)
y = data['language']

print(data.head())

X_train, X_test, y_train, y_test = model_selection.train_test_split(X, y, 
                                    test_size=0.2, random_state=1)

print(len(y_train))
print(len(y_test))

scaler = StandardScaler()
scaler.fit(X_train)
X_train = scaler.transform(X_train)
X_test = scaler.transform(X_test)

"""Decision Tree;"""

dtree = tree.DecisionTreeClassifier(criterion='entropy', max_depth=5) #, min_samples_split=4)
dtree.fit(X_train, y_train)
y_train_pred = dtree.predict(X_train)
y_pred = dtree.predict(X_test)

acc_train = metrics.accuracy_score(y_train, y_train_pred)
print("DT acc_train: %f" %acc_train )
acc = metrics.accuracy_score(y_test, y_pred)
print("DT acc: %f" %acc )

recall_train = metrics.recall_score(y_train, y_train_pred, average=None)
print("DT recall_train:", recall_train )
recall = metrics.recall_score(y_test, y_pred, average=None)
print("DT recall: %f",  recall )

prec_train = metrics.precision_score(y_train, y_train_pred, average=None)
print("DT precision_train:", prec_train )
prec = metrics.precision_score(y_test, y_pred, average=None)
print("DT precision:", prec )

f1_train = metrics.f1_score(y_train, y_train_pred, average=None)
print("DT f1 train:", f1_train )
f1 = metrics.f1_score(y_test, y_pred, average=None)
print("DT f1:", f1 )

conf_mat = metrics.confusion_matrix(y_test, y_pred)
print("DT conf_mat: %f")
print(conf_mat)

"""Random Forest Classifier"""

from sklearn import ensemble

rf = ensemble.RandomForestClassifier(criterion='entropy') #, max_depth=4)
rf.fit(X_train, y_train)
y_train_pred = rf.predict(X_train)
y_pred = rf.predict(X_test)

acc_train = metrics.accuracy_score(y_train, y_train_pred)
print("RF acc_train: %f" %acc_train )
acc = metrics.accuracy_score(y_test, y_pred)
print("RF acc: %f" %acc )

recall_train = metrics.recall_score(y_train, y_train_pred, average=None)
print("RF recall_train:", recall_train )
recall = metrics.recall_score(y_test, y_pred, average=None)
print("RF recall:", recall )

prec_train = metrics.precision_score(y_train, y_train_pred, average=None)
print("RF precision_train:", prec_train )
prec = metrics.precision_score(y_test, y_pred, average=None)
print("RF precision:", prec )

f1_train = metrics.f1_score(y_train, y_train_pred, average=None)
print("RF f1 train:", f1_train )
f1 = metrics.f1_score(y_test, y_pred, average=None)
print("RF f1:", f1 )

conf_mat = metrics.confusion_matrix(y_test, y_pred)
print("RF conf_mat:")
print(conf_mat)

"""SVM"""

from sklearn import svm

svc_cls = svm.SVC()
svc_cls.fit(X_train, y_train)
y_train_pred = svc_cls.predict(X_train)
y_pred = svc_cls.predict(X_test)

from sklearn import metrics
acc_train = metrics.accuracy_score(y_train, y_train_pred)
print("SVC acc_train:", acc_train )
acc = metrics.accuracy_score(y_test, y_pred)
print("SVC acc:", acc )

recall_train = metrics.recall_score(y_train, y_train_pred, average=None)
print("SVC recall_train:", recall_train )
recall = metrics.recall_score(y_test, y_pred, average=None)
print("SVC recall:", recall )

prec_train = metrics.precision_score(y_train, y_train_pred, average=None)
print("SVC precision_train:", prec_train )
prec = metrics.precision_score(y_test, y_pred, average=None)
print("SVC precision:", prec )

f1_train = metrics.f1_score(y_train, y_train_pred, average=None)
print("SVC f1 train:", f1_train )
f1 = metrics.f1_score(y_test, y_pred, average=None)
print("SVC f1:", f1 )

conf_mat = metrics.confusion_matrix(y_test, y_pred)
print("SVC conf_mat: %f")
print(conf_mat)

"""Nearest neighbours"""

from sklearn import neighbors

knn = neighbors.KNeighborsClassifier(n_neighbors=3)
knn.fit(X_train, y_train)
y_train_pred = knn.predict(X_train)
y_pred = knn.predict(X_test)

from sklearn import metrics
acc_train = metrics.accuracy_score(y_train, y_train_pred)
print("kNN acc_train:", acc_train )
acc = metrics.accuracy_score(y_test, y_pred)
print("kNN acc:", acc )

recall_train = metrics.recall_score(y_train, y_train_pred, average=None)
print("kNN recall_train:", recall_train)
recall = metrics.recall_score(y_test, y_pred, average=None)
print("kNN recall:", recall)

prec_train = metrics.precision_score(y_train, y_train_pred, average=None)
print("kNN precision_train:", prec_train )
prec = metrics.precision_score(y_test, y_pred, average=None)
print("kNN precision:", prec )

f1_train = metrics.f1_score(y_train, y_train_pred, average=None)
print("kNN f1 train:", f1_train )
f1 = metrics.f1_score(y_test, y_pred, average=None)
print("kNN f1:", f1 )

conf_mat = metrics.confusion_matrix(y_test, y_pred)
print("kNN conf_mat:")
print(conf_mat)