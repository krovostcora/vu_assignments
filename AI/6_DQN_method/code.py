from torchvision.transforms.functional import normalize
from torchvision.io.image import read_image
from sklearn.metrics import accuracy_score
from torchvision.models import inception_v3
import matplotlib.pyplot as plt
import torchvision
import numpy as np
import json
import os

# Loading the pre-trained model
resnet = torchvision.models.resnet18(weights='DEFAULT').eval()
efficientnet = torchvision.models.efficientnet_b0(weights='DEFAULT').eval()
inception = inception_v3(pretrained=True).eval()

# Loadin the imagenet_class_index.json file to map class index to label
with open('imagenet_class_index.json') as labels_file:
   labels = json.load(labels_file)

# Defining the path to the folder containing the images
folder_path = 'goldfish'

# Initializing lists to collect the predicted and actual labels
temp_predicted_labels = []
actual_labels = []

# Creating a list of tuples, each containing a model and its name
models = [
    ('ResNet-18', resnet),
    ('Inception-v3', inception),
    ('EfficientNet-B0', efficientnet)
]

def test_model(model_name, model):

   # Iterating over each image file in the folder and apply the script
   for filename in os.listdir(folder_path):
       if filename.endswith('.jpg') or filename.endswith('.jpeg') or filename.endswith('.png'):

           # Loading the image and transform it to match the format required by the model
           image_path = os.path.join(folder_path, filename)
           img = read_image(image_path)

           plt.imshow(img.permute(1, 2, 0))
           input_tensor = normalize(img / 255., [0.485, 0.456, 0.406], [0.229, 0.224, 0.225])
           out = model(input_tensor.unsqueeze(0))
           out = out.detach().cpu().numpy()
           idx = np.argmax(out, axis=1)
           str_idx = str(idx[0])
           predicted_label = labels[str_idx]
           print(f" {model_name} : {predicted_label}")

           actual_label = 'goldfish'

           # Collecting the predicted and actual labels
           temp_predicted_labels.append(predicted_label[1])
           actual_labels.append(actual_label)

   predicted_labels = []
   for label in temp_predicted_labels:
       if 'goldfish' in label:
           predicted_labels.append('goldfish')
       else:
           predicted_labels.append(label)

   accuracy = accuracy_score(actual_labels, predicted_labels)
   print('Accuracy: {:.2f}%\n'.format(accuracy * 100))

for model_name, model in models:
    test_model(model_name, model)
