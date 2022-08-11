import json
import os
import unittest
from unittest import skip

import requests

from fixtures import data as data_store


class TestIntegration(unittest.TestCase):


    def test_create_recipe(self):
        url = "http://localhost:8080/recipe/"

        data1 = data_store.create_item_request_1
        data2 = data_store.create_item_request_2

        headers = {
          'Content-Type': 'application/json'
        }

        response = requests.request("POST", url, headers=headers, data=json.dumps(data1))
        assert response.status_code == 201

        response = requests.request("POST", url, headers=headers, data=json.dumps(data2))
        assert response.status_code == 201



    def test_get_recipe_without_filter(self):
        url = "http://localhost:8080/recipe/"
        headers = {
            'Content-Type': 'application/json'
        }

        response = requests.request("GET", url, headers=headers)
        assert len(json.loads(response.text)) == 2

        url = "http://localhost:8080/recipe/?type=veg"
        headers = {
            'Content-Type': 'application/json'
        }

        response = requests.request("GET", url, headers=headers)
        assert len(json.loads(response.text)) == 1

        response_list = json.loads(response.text)
        for r in response_list:
            assert r["type"] == "veg"


        #Delete recipe
        url = "http://localhost:8080/recipe/fries"
        response = requests.request("DELETE", url, headers=headers)
        assert response.status_code == 200

        #Delete recipe
        url = "http://localhost:8080/recipe/omlet"
        response = requests.request("DELETE", url, headers=headers)
        assert response.status_code == 200


    def test_update_recipe(self):

        #Add new recipe
        url = "http://localhost:8080/recipe/"
        headers = {
            'Content-Type': 'application/json'
        }

        data3 = data_store.create_item_request_3
        response = requests.request("POST", url, headers=headers, data=json.dumps(data3))
        assert response.status_code == 201

        #Update recipe

        url = "http://localhost:8080/recipe/pizza"
        data = data_store.update_recipe_request
        response = requests.request("PUT", url, headers=headers, data= json.dumps(data))
        assert response.status_code == 200

        #Delete Recipe
        response = requests.request("DELETE", url, headers=headers)
        assert response.status_code == 200

    def test_update_recipe_same_recipe(self):
        #Add new recipe
        url = "http://localhost:8080/recipe/"
        headers = {
            'Content-Type': 'application/json'
        }
        data4 = data_store.create_item_request_4
        response = requests.request("POST", url, headers=headers, data=json.dumps(data4))
        assert response.status_code == 201

        #update recipe
        url = "http://localhost:8080/recipe/soup"
        data = data_store.update_recipe_request_2
        response = requests.request("PUT", url, headers=headers, data= json.dumps(data))
        assert response.status_code == 400

        #Delete Recipe
        response = requests.request("DELETE", url, headers=headers)
        assert response.status_code == 200

    def test_delete_recipe(self):
        #Add recipe
        url = "http://localhost:8080/recipe/"
        headers = {
            'Content-Type': 'application/json'
        }
        data4 = data_store.create_item_request_5
        response = requests.request("POST", url, headers=headers, data=json.dumps(data4))

        #Delete recipe
        url = "http://localhost:8080/recipe/popcorn"
        response = requests.request("DELETE", url, headers=headers)
        assert response.status_code == 200




def test_delete_recipe_non_existent_recipe(self):
        url = "http://localhost:8080/recipe/salad"
        headers = {
            'Content-Type': 'application/json'
        }

        response = requests.request("DELETE", url, headers=headers)

        assert response.status_code == 404


if __name__ == '__main__':
    unittest.main()
