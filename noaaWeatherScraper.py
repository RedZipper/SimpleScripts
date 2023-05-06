import requests
import os
from datetime import datetime as dt
from bs4 import BeautifulSoup

#Scrape NOAA's Climate Prediction Center page for 6-10 day temperature outlook
#URL
url = 'https://www.cpc.ncep.noaa.gov/products/predictions/610day/'

response = requests.get(url)
soup = BeautifulSoup(response.text, 'html.parser')

# Example: Extract the link to the temperature outlook image
#<img src="./610temp.new.gif" border="0" width="540" alt="6 to 10 Day Outlook - Temperature Probability">

image_relative_path = soup.find('img', {'alt': '6 to 10 Day Outlook - Temperature Probability'})['src'][2:]
# Create an output directory
output_dir = 'GIFs'
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

# Download and save the image
img_url = f'https://www.cpc.ncep.noaa.gov/products/predictions/610day/{image_relative_path}'
img_data = requests.get(img_url).content

with open(os.path.join(output_dir, f'6-10_day_temp_outlook{dt.now()}.gif'), 'wb') as f:
    f.write(img_data)
