import json
import os
import unittest
from unittest import skip

import requests

from fixtures import data as data_store


class TestIntegration(unittest.TestCase):

    @skip
    def test_create_recipe(self):
        url = "http://localhost:8080/recipe/new"

        data1 = data_store.create_item_request_1
        data2 = data_store.create_item_request_2
        data3 = data_store.create_item_request_3
        data4 = data_store.create_item_request_4

        headers = {
          'Content-Type': 'application/json'
        }

        response = requests.request("POST", url, headers=headers, data=json.dumps(data1))
        assert response.status_code == 201

        response = requests.request("POST", url, headers=headers, data=json.dumps(data2))
        assert response.status_code == 201

        response = requests.request("POST", url, headers=headers, data=json.dumps(data3))
        assert response.status_code == 201

        response = requests.request("POST", url, headers=headers, data=json.dumps(data4))
        assert response.status_code == 201

    def test_get_recipe_without_filter(self):
        url = "http://localhost:8080/recipe/"
        headers = {
            'Content-Type': 'application/json'
        }

        response = requests.request("GET", url, headers=headers)
        print(json.loads(response.text))
        assert len(json.loads(response.text)) == 4

    def test_get_recipe_with_filter(self):
        url = "http://localhost:8080/recipe/"
        headers = {
            'Content-Type': 'application/json'
        }
        filter = data_store.get_recipe_filter_all_veg_items

        response = requests.request("GET", url, headers=headers, data= json.dumps(filter))
        assert len(json.loads(response.text)) == 2

        response_list = json.loads(response.text)
        for r in response_list:
            assert r["type"] == "veg"

if __name__ == '__main__':
    unittest.main()
