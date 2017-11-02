/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.core.api.score.constraint;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.api.score.Score;

/**
 * Retrievable from {@link ConstraintMatchTotal#getConstraintMatchSet()}.
 */
public final class ConstraintMatch implements Serializable, Comparable<ConstraintMatch> {

    protected final String constraintPackage;
    protected final String constraintName;

    protected final List<Object> justificationList;
    protected final Score score;

    /**
     * @param constraintPackage never null
     * @param constraintName never null
     * @param justificationList never null, sometimes empty
     * @param score never null
     */
    public ConstraintMatch(String constraintPackage, String constraintName,
            List<Object> justificationList, Score score) {
        this.constraintPackage = constraintPackage;
        this.constraintName = constraintName;
        this.justificationList = justificationList;
        this.score = score;
    }

    public String getConstraintPackage() {
        return constraintPackage;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public List<Object> getJustificationList() {
        return justificationList;
    }

    public Score getScore() {
        return score;
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    public String getConstraintId() {
        return constraintPackage + "/" + constraintName;
    }

    public String getIdentificationString() {
        return getConstraintId() + "/" + justificationList;
    }

    @Override
    public int compareTo(ConstraintMatch other) {
        if (!constraintPackage.equals(other.constraintPackage)) {
            return constraintPackage.compareTo(other.constraintPackage);
        } else if (!constraintName.equals(other.constraintName)) {
            return constraintName.compareTo(other.constraintName);
        } else {
            for (int i = 0; i < justificationList.size() && i < other.justificationList.size(); i++) {
                int comparison = ((Comparable) justificationList.get(i)).compareTo(other.justificationList.get(i));
                if (comparison != 0) {
                    return comparison;
                }
            }
            if (justificationList.size() != other.justificationList.size()) {
                return justificationList.size() < other.justificationList.size() ? -1 : 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof ConstraintMatch) {
            ConstraintMatch other = (ConstraintMatch) o;
            return constraintPackage.equals(other.constraintPackage)
                    && constraintName.equals(other.constraintName)
                    && justificationList.equals(other.justificationList);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (((17 * 37)
                + constraintPackage.hashCode()) * 37
                + constraintName.hashCode()) * 37
                + justificationList.hashCode();
    }

    @Override
    public String toString() {
        return getIdentificationString() + "=" + getScore();
    }

}
