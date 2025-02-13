#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Mar 20 08:09:21 2023

@author: gintautas
"""

import numpy as np
import cv2
import torch
import torchvision
from torchvision.models import resnet18

from torchvision.transforms.functional import normalize, resize

import matplotlib.pyplot as plt
import json

with open('imagenet_class_index.json') as labels_file:
    labels = json.load(labels_file)

model = resnet18(pretrained=True).eval()

file = 'cat_224x224.jpg'
#img = torchvision.io.read_image(file)
image  = cv2.imread(file)
image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
img = torch.from_numpy(image)
img = img.permute(2,0,1)

plt.figure()
plt.imshow(image)
input_tensor = normalize(img / 255., [0.485, 0.456, 0.406], [0.229, 0.224, 0.225])
out = model(input_tensor.unsqueeze(0))
out = out.detach().cpu().numpy()
idx = np.argmax(out, axis=1)
str_idx = str(idx[0])
print(labels[str_idx])

file = 'dog.jpeg'
#img = read_image(file)
image  = cv2.imread(file)
image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
img = torch.from_numpy(image)
img = img.permute(2,0,1)
print('Image shape:', img.shape)
plt.imshow(image)
input_tensor = normalize(resize(img, (224, 224)) / 255., [0.485, 0.456, 0.406], [0.229, 0.224, 0.225])
out = model(input_tensor.unsqueeze(0))
out = out.detach().cpu().numpy()
idx = np.argmax(out, axis=1)
str_idx = str(idx[0])
print(labels[str_idx])

