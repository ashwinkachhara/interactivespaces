##
# Copyright (C) 2013 Google Inc.
# 
# Licensed under the Apache License, Version 2.0 (the "License"); you may not
# use this file except in compliance with the License. You may obtain a copy of
# the License at
# 
# http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations under
# the License.
##

# The location of a resource in the Interactoive Spaces container.

int32 main_location
int32 LOCATION_SYSTEM_BOOTSTRAP = 0
int32 LOCATION_USER_BOOTSTRAP = 1
int32 LOCATION_LIB_SYSTEM = 2
int32 LOCATION_CONFIG = 3
int32 LOCATION_ROOT = 4

# The location relative to the main location, if it makes sense for that location
string sub_location