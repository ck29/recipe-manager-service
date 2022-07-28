create_item_request_1={"name": "fries","type": "veg","ingredients": [ "potato"],"serves": 2,"instructions": "bake in oven."}
create_item_request_2={"name": "omlet","type": "non veg","ingredients": ["egg", "onion"],"serves": 1,"instructions": "fry on pan."}
create_item_request_3={"name": "pizza","type": "non veg","ingredients": ["cheeze", "wheat", "tomato", "mushroom"],"serves": 2,"instructions": "bake in oven."}
create_item_request_4={"name": "soup","type": "veg","ingredients": ["cream", "mushroom"],"serves": 2,"instructions": "boil and grind."}

get_recipe_filter_all_veg_items ={
    "expression":"(#type=:type)",
    "attributeNames": {
        "#type": "type"
    },
    "attributeValues":{
        ":type": "veg"
    }
}

update_recipe_request = {
    "existing": {
        "name": "fries",
        "type": "veg",
        "ingredients": ["potato"],
        "serves": 2,
        "instructions": "bake in oven."
    },
    "new": {
        "name": "fries",
        "type": "veg",
        "ingredients": ["potato", "oil"],
        "serves": 3,
        "instructions": "bake in oven."
    }
}

update_recipe_request_2 = {
    "existing": {
        "name": "fries",
        "type": "veg",
        "ingredients": ["potato", "oil"],
        "serves": 3,
        "instructions": "bake in oven."
    },
    "new": {
        "name": "fries",
        "type": "veg",
        "ingredients": ["potato", "oil"],
        "serves": 3,
        "instructions": "bake in oven."
    }
}

delete_recipe_request = {"name": "soup", "type": "veg"}
delete_recipe_request_2 = {"name": "salad", "type": "veg"}
