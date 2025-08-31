package br.com.matkabal.pokefetcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {

    private int id;

    private String name;

    @JsonProperty("base_experience")
    private Integer baseExperience;

    private Integer height;

    @JsonProperty("is_default")
    private Boolean isDefault;

    private Integer order;

    private Integer weight;

    private List<Ability> abilities;

    @JsonProperty("forms")
    private List<NamedAPIResource> forms;

    @JsonProperty("game_indices")
    private List<GameIndex> gameIndices;

    @JsonProperty("held_items")
    private List<HeldItem> heldItems;

    @JsonProperty("location_area_encounters")
    private String locationAreaEncounters;

    private List<Move> moves;

    private Species species;

    private Sprites sprites;

    private List<Stat> stats;

    private List<Type> types;

    // ===== Subtipos =====

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Ability {
        private boolean isHidden;
        private int slot;
        private AbilityInfo ability;

        @JsonProperty("is_hidden")
        public void setIsHidden(boolean isHidden) { this.isHidden = isHidden; }

        @JsonProperty("slot")
        public void setSlot(int slot) { this.slot = slot; }

        @JsonProperty("ability")
        public void setAbility(AbilityInfo ability) { this.ability = ability; }
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AbilityInfo extends NamedAPIResource {}

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GameIndex {
        @JsonProperty("game_index")
        private int gameIndex;
        private Version version;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Version extends NamedAPIResource {}

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HeldItem {
        private Item item;

        @JsonProperty("version_details")
        private List<HeldItemVersion> versionDetails;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item extends NamedAPIResource {}

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HeldItemVersion {
        private Version version;

        @JsonProperty("rarity")
        private int rarity;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Move {
        private MoveInfo move;

        @JsonProperty("version_group_details")
        private List<MoveVersionGroupDetail> versionGroupDetails;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MoveInfo extends NamedAPIResource {}

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MoveVersionGroupDetail {
        @JsonProperty("level_learned_at")
        private int levelLearnedAt;

        @JsonProperty("version_group")
        private VersionGroup versionGroup;

        @JsonProperty("move_learn_method")
        private MoveLearnMethod moveLearnMethod;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VersionGroup extends NamedAPIResource {}

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MoveLearnMethod extends NamedAPIResource {}

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Species extends NamedAPIResource {}

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Stat {
        @JsonProperty("base_stat")
        private int baseStat;

        private int effort;

        private StatInfo stat;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StatInfo extends NamedAPIResource {}

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Type {
        private int slot;
        private TypeInfo type;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TypeInfo extends NamedAPIResource {}


    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sprites {
        @JsonProperty("front_default")
        private String frontDefault;

        @JsonProperty("front_shiny")
        private String frontShiny;

        @JsonProperty("front_female")
        private String frontFemale;

        @JsonProperty("front_shiny_female")
        private String frontShinyFemale;

        @JsonProperty("back_default")
        private String backDefault;

        @JsonProperty("back_shiny")
        private String backShiny;

        @JsonProperty("back_female")
        private String backFemale;

        @JsonProperty("back_shiny_female")
        private String backShinyFemale;

        private Map<String, Object> other;
        private Map<String, Object> versions;
    }


    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NamedAPIResource {
        private String name;
        private String url;
    }
}
